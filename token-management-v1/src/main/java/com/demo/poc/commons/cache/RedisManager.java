package com.demo.poc.commons.cache;

import com.demo.poc.commons.properties.ApplicationProperties;
import com.demo.poc.commons.properties.cache.TimeToLive;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.demo.poc.commons.properties.cache.TimeToLive.FIVE_MINUTES;

@Slf4j
public class RedisManager implements CacheManager {

  private final RedisConnectionFactory redisConnectionFactory;
  private final ApplicationProperties properties;
  private final RedisCacheManager redisCacheManager;

  public RedisManager(RedisConnectionFactory redisConnectionFactory,
                      ApplicationProperties properties) {
    this.properties = properties;
    this.redisConnectionFactory = redisConnectionFactory;
    this.redisCacheManager = createRedisCacheManager();
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

  private RedisCacheManager createRedisCacheManager() {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    properties.getCache()
        .forEach((configName, cacheTemplate) -> {
          String cacheKey = cacheTemplate.getKeyPrefix();
          Duration ttl = TimeToLive.getTtl(cacheTemplate.getTimeToLive());
          RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(ttl);
          cacheConfigurations.put(cacheKey, cacheConfig);
        });

    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(FIVE_MINUTES.getTimeToLive());

    return RedisCacheManager.builder(redisConnectionFactory)
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
