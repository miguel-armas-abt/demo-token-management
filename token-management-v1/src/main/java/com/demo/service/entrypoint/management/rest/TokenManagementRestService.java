package com.demo.service.entrypoint.management.rest;

import java.util.Map;

import com.demo.commons.validations.ParamValidator;
import com.demo.service.entrypoint.management.enums.Platform;
import com.demo.service.entrypoint.management.params.clean.CleanTokenHeader;
import com.demo.service.entrypoint.management.params.generate.GenerateTokenHeader;
import com.demo.service.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import com.demo.service.entrypoint.management.service.TokenManagementService;
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
  private final ParamValidator paramValidator;

  @PostMapping
  public ResponseEntity<TokenResponseWrapper> generateToken(HttpServletRequest servletRequest) {
    Map.Entry<GenerateTokenHeader, Map<String, String>> headersEntry = paramValidator.validateHeadersAndGet(servletRequest, GenerateTokenHeader.class);
    return ResponseEntity.ok(tokenManagementService.generateToken(headersEntry.getValue(), Platform.parse(headersEntry.getKey().getPlatform())));
  }

  @DeleteMapping
  public ResponseEntity<?> cleanToken(HttpServletRequest servletRequest) {
    Map.Entry<CleanTokenHeader, Map<String, String>> headersEntry = paramValidator.validateHeadersAndGet(servletRequest, CleanTokenHeader.class);
    tokenManagementService.cleanToken(headersEntry.getValue(), Platform.parse(headersEntry.getKey().getPlatform()));
    return ResponseEntity.noContent().build();
  }
}
