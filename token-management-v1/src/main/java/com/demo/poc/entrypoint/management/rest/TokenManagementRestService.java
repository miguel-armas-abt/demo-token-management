package com.demo.poc.entrypoint.management.rest;

import java.util.Map;

import com.demo.poc.commons.core.restserver.utils.RestServerUtils;
import com.demo.poc.commons.core.validations.ParamValidator;
import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.entrypoint.management.params.clean.CleanTokenHeader;
import com.demo.poc.entrypoint.management.params.generate.GenerateTokenHeader;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import com.demo.poc.entrypoint.management.service.TokenManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/token-management/v1/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenManagementRestService {

  private final TokenManagementService tokenManagementService;
  private final ParamValidator headerValidator;

  @PostMapping
  public ResponseEntity<TokenResponseWrapper> generateToken(HttpServletRequest servletRequest) {
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);
    GenerateTokenHeader generateTokenHeader = headerValidator.validateAndGet(headers, GenerateTokenHeader.class);
    return ResponseEntity.ok(tokenManagementService.generateToken(headers, Platform.parse(generateTokenHeader.getPlatform())));
  }

  @DeleteMapping
  public ResponseEntity<?> cleanToken(HttpServletRequest servletRequest) {
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);
    CleanTokenHeader cleanTokenHeader = headerValidator.validateAndGet(headers, CleanTokenHeader.class);
    tokenManagementService.cleanToken(headers, Platform.parse(cleanTokenHeader.getPlatform()));
    return ResponseEntity.noContent().build();
  }
}
