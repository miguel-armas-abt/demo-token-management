package com.demo.poc.commons.custom.cache;

import com.demo.poc.commons.custom.properties.ApplicationProperties;
import com.demo.poc.commons.custom.properties.cache.TimeToLive;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.demo.poc.commons.custom.properties.cache.TimeToLive.FIVE_MINUTES;

@Slf4j
@RequiredArgsConstructor
public class RedisManager implements CacheManager {

  private final RedisConnectionFactory redisConnectionFactory;
  private final ApplicationProperties properties;
  private RedisCacheManager redisCacheManager;

  @PostConstruct
  public void init() {
    redisCacheManager = createRedisCacheManager();
  }

  @Override
  public Cache getCache(String name) {
    return isRedisAvailable()
        ? redisCacheManager.getCache(name)
        : new ConcurrentMapCache(name);
  }

  @Override
  public Collection<String> getCacheNames() {
    return redisCacheManager.getCacheNames();
  }

  private org.springframework.data.redis.cache.RedisCacheManager createRedisCacheManager() {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    properties.getCache().forEach((key, value) ->
        cacheConfigurations.put(key, RedisCacheConfiguration.defaultCacheConfig().entryTtl(TimeToLive.getTtl(value.getTimeToLive()))));

    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(FIVE_MINUTES.getTimeToLive());

    return org.springframework.data.redis.cache.RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(defaultConfig)
        .withInitialCacheConfigurations(cacheConfigurations)
        .build();
  }

  public boolean isRedisAvailable() {
    try {
      redisConnectionFactory.getConnection().ping();
      return true;
    } catch (Exception exception) {
      log.error(exception.getMessage());
      return false;
    }
  }
}
