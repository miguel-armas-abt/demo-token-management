package com.demo.poc.entrypoint.management.helper;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.demo.poc.commons.properties.cache.TimeToLive;
import com.demo.poc.commons.cache.RedisManager;
import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.commons.properties.ApplicationProperties;
import com.demo.poc.entrypoint.management.enums.ClockSkew;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCacheHelperV2 implements TokenCacheHelper {

  private static final String CACHE_NAME = "tokens";

  private final ApplicationProperties properties;
  private final RedisTemplate<String, Object> redisTemplate;
  private final RedisManager redisManager;
  private final List<TokenRepository> tokenRepositories;
  private final Gson gson;

  @Override
  public TokenResponseWrapper generateToken(Map<String, String> headers, Platform platform) {
    return Optional.of(redisManager.isRedisAvailable())
        .filter(isRedisAvailable -> isRedisAvailable)
        .map(isRedisAvailable -> getTokenFromCacheIfPresent(headers, platform, buildCacheKey(platform)))
        .orElseGet(() -> this.selectRepository(platform, tokenRepositories).generateToken(headers));
  }

  private TokenResponseWrapper getTokenFromCacheIfPresent(Map<String, String> headers, Platform platform, String cacheKey) {
    String tokenJson = Optional.ofNullable((String) redisTemplate.opsForValue().get(cacheKey))
        .orElseGet(() -> {
          TokenResponseWrapper tokenResponse = this.selectRepository(platform, tokenRepositories).generateToken(headers);
          TimeToLive timeToLive = properties.searchCache(CACHE_NAME).getTimeToLive();
          Duration ttl = ClockSkew.getTtlWithClockSkew(tokenResponse, timeToLive);
          redisTemplate.opsForValue().set(cacheKey, gson.toJson(tokenResponse), ttl);
          return  (String) redisTemplate.opsForValue().get(cacheKey);
        });
    return gson.fromJson(tokenJson, TokenResponseWrapper.class);
  }

  @Override
  public void cleanToken(Map<String, String> headers, Platform platform) {
    if(redisManager.isRedisAvailable()) {
      String cacheKey = buildCacheKey(platform);
      redisTemplate.delete(cacheKey);
    }
  }

  private String buildCacheKey(Platform platform) {
    return properties.searchCache(CACHE_NAME).getKeyPrefix() + ":" + platform.name();
  }

  @Override
  public boolean supports(Class<?> selectedClass) {
    return this.getClass().isAssignableFrom(selectedClass);
  }
}
