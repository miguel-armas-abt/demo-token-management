package com.demo.poc.entrypoint.management.helper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.demo.poc.commons.custom.cache.CacheConstant;
import com.demo.poc.commons.custom.cache.RedisManager;
import com.demo.poc.commons.custom.enums.Platform;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCacheHelperV2 implements TokenCacheHelper {

  private final RedisTemplate<String, Object> redisTemplate;
  private final RedisManager redisManager;
  private final List<TokenRepository> tokenRepositories;
  private final Gson gson;

  @Override
  public TokenResponseWrapper getToken(Platform platform) {
    return Optional.of(redisManager.isRedisAvailable())
        .filter(isRedisAvailable -> isRedisAvailable)
        .map(isRedisAvailable -> buildCacheTokenKey(platform))
        .map(cacheKey -> Optional.ofNullable((String) redisTemplate.opsForValue().get(cacheKey))
            .map(tokenJson -> gson.fromJson(tokenJson, TokenResponseWrapper.class))
            .orElseGet(() -> {
              TokenResponseWrapper tokenResponse = this.selectRepository(platform, tokenRepositories).getToken();
              Long ttl = Optional.ofNullable((long) tokenResponse.getExpiresIn())
                  .map(expireTime -> expireTime - 30L)
                  .orElse(300L);
              redisTemplate.opsForValue().set(cacheKey, gson.toJson(tokenResponse), ttl, TimeUnit.SECONDS);
              String tokenJson = (String) redisTemplate.opsForValue().get(cacheKey);
              return gson.fromJson(tokenJson, TokenResponseWrapper.class);
            }))
        .orElseGet(() -> this.selectRepository(platform, tokenRepositories).getToken());
  }

  @Override
  public void cleanToken(Platform platform) {
    String cacheKey = buildCacheTokenKey(platform);
    redisTemplate.delete(cacheKey);
  }

  private static String buildCacheTokenKey(Platform platform) {
    return CacheConstant.TOKEN_CACHE_NAME + ":" + platform.name();
  }

  @Override
  public boolean supports(Class<?> selectedClass) {
    return this.getClass().isAssignableFrom(selectedClass);
  }
}
