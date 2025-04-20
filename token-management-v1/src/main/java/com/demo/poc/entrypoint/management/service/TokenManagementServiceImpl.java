package com.demo.poc.entrypoint.management.service;

import java.util.List;
import java.util.Map;

import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import com.demo.poc.entrypoint.management.helper.TokenCacheHelper;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenManagementServiceImpl implements TokenManagementService {

  private final List<TokenCacheHelper> tokenCacheHelpers;
  private final ApplicationProperties properties;

  @Override
  public TokenResponseWrapper getToken(Map<String, String> headers, Platform platform) {
    return this.selectCacheHelper(tokenCacheHelpers, properties).getToken(headers, platform);
  }

  @Override
  public void cleanToken(Map<String, String> headers, Platform platform) {
    this.selectCacheHelper(tokenCacheHelpers, properties).cleanToken(headers, platform);
  }
}
