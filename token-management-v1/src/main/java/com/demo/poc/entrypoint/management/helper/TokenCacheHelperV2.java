package com.demo.poc.entrypoint.management.helper;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import com.demo.poc.commons.custom.properties.cache.TimeToLive;
import com.demo.poc.entrypoint.management.constants.CacheConstant;
import com.demo.poc.commons.custom.cache.RedisManager;
import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import com.demo.poc.entrypoint.management.enums.ClockSkew;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import com.demo.poc.entrypoint.management.utils.CacheUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCacheHelperV2 implements TokenCacheHelper {

  private final ApplicationProperties properties;
  private final RedisTemplate<String, Object> redisTemplate;
  private final RedisManager redisManager;
  private final List<TokenRepository> tokenRepositories;
  private final Gson gson;

  @Override
  public TokenResponseWrapper getToken(Platform platform) {
    return Optional.of(redisManager.isRedisAvailable())
        .filter(isRedisAvailable -> isRedisAvailable)
        .map(isRedisAvailable -> getTokenFromCacheIfPresent(platform, CacheUtils.buildCacheKey(platform)))
        .orElseGet(() -> this.selectRepository(platform, tokenRepositories).getToken());
  }

  private TokenResponseWrapper getTokenFromCacheIfPresent(Platform platform, String cacheKey) {
    String tokenJson = Optional.ofNullable((String) redisTemplate.opsForValue().get(cacheKey))
        .orElseGet(() -> {
          TokenResponseWrapper tokenResponse = this.selectRepository(platform, tokenRepositories).getToken();
          TimeToLive timeToLive = properties.getCache().get(CacheConstant.TOKEN_CACHE_NAME).getTimeToLive();
          Duration ttl = ClockSkew.getTtlWithClockSkew(tokenResponse, timeToLive);
          redisTemplate.opsForValue().set(cacheKey, gson.toJson(tokenResponse), ttl);
          return  (String) redisTemplate.opsForValue().get(cacheKey);
        });
    return gson.fromJson(tokenJson, TokenResponseWrapper.class);
  }

  @Override
  public void cleanToken(Platform platform) {
    if(redisManager.isRedisAvailable()) {
      String cacheKey = CacheUtils.buildCacheKey(platform);
      redisTemplate.delete(cacheKey);
    }
  }

  @Override
  public boolean supports(Class<?> selectedClass) {
    return this.getClass().isAssignableFrom(selectedClass);
  }
}
