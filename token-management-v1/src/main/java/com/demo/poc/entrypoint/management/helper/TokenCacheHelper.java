package com.demo.poc.entrypoint.management.helper;

import java.util.List;

import com.demo.poc.commons.custom.enums.Platform;
import com.demo.poc.commons.custom.exceptions.NoSuchTokenRepositoryException;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;

public interface TokenCacheHelper {

  String TOKEN_CACHE_HELPER_SELECTOR = "token-cache-helper";

  TokenResponseWrapper getToken(Platform platform);

  void cleanToken(Platform platform);

  boolean supports(Class<?> selectedClass);

  default TokenRepository selectRepository(Platform platform, List<TokenRepository> tokenRepositories) {
    return tokenRepositories.stream()
        .filter(tokenRepository -> tokenRepository.supports(platform))
        .findFirst()
        .orElseThrow(NoSuchTokenRepositoryException::new);
  }
}
