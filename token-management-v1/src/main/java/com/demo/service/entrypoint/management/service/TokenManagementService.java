package com.demo.service.entrypoint.management.service;

import java.util.List;
import java.util.Map;

import com.demo.service.entrypoint.management.enums.Platform;
import com.demo.service.entrypoint.management.exceptions.NoSuchTokenCacheHelperException;
import com.demo.service.commons.properties.ApplicationProperties;
import com.demo.service.entrypoint.management.helper.TokenCacheHelper;
import com.demo.service.entrypoint.management.repository.wrapper.TokenResponseWrapper;

public interface TokenManagementService {

  TokenResponseWrapper generateToken(Map<String, String> headers, Platform platform);

  void cleanToken(Map<String, String> headers, Platform platform);

  default TokenCacheHelper selectCacheHelper(List<TokenCacheHelper> tokenCacheHelpers,
                                             ApplicationProperties properties) {

    Class<?> selectedClass = properties.getSelectorClass().get(TokenCacheHelper.TOKEN_CACHE_HELPER_SELECTOR);
    return tokenCacheHelpers.stream()
        .filter(tokenCacheHelper -> tokenCacheHelper.supports(selectedClass))
        .findFirst()
        .orElseThrow(NoSuchTokenCacheHelperException::new);
  }
}
