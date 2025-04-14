package com.demo.poc.entrypoint.management.utils;

import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.entrypoint.management.constants.CacheConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtils {

  private static final String COLON = ":";

  public static String buildCacheKey(Platform platform) {
    return CacheConstant.TOKEN_CACHE_NAME + COLON + platform.name();
  }

}
