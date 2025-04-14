package com.demo.poc.commons.custom.properties;

import java.util.Map;
import java.util.Optional;

import com.demo.poc.commons.core.errors.exceptions.NoSuchCacheConfigException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientException;
import com.demo.poc.commons.custom.properties.cache.CacheTemplate;
import com.demo.poc.commons.custom.properties.restclient.RestClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "configuration")
public class ApplicationProperties {

  private Map<String, String> errorMessages;

  private Map<String, RestClient> restClients;

  private Map<String, CacheTemplate> cache;

  private Map<String, Class<?>> selectorClass;

  public String searchEndpoint(String serviceName) {
    return searchRestClient(serviceName).getEndpoint();
  }

  private RestClient searchRestClient(String serviceName) {
    return Optional.ofNullable(restClients.get(serviceName))
        .orElseThrow(NoSuchRestClientException::new);
  }

  public CacheTemplate searchCache(String cacheName) {
    return Optional.ofNullable(cache.get(cacheName))
        .orElseThrow(NoSuchCacheConfigException::new);
  }
}
