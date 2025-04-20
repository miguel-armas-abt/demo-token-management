package com.demo.poc.entrypoint.management.rest;

import java.util.Map;

import com.demo.poc.commons.core.restserver.utils.ServerHeaderExtractor;
import com.demo.poc.commons.core.validations.headers.DefaultHeaders;
import com.demo.poc.commons.core.validations.headers.HeaderValidator;
import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import com.demo.poc.entrypoint.management.service.TokenManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/token-management/v1/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenManagementRestService {

  private final TokenManagementService tokenManagementService;
  private final HeaderValidator headerValidator;

  @GetMapping
  public ResponseEntity<TokenResponseWrapper> getToken(HttpServletRequest servletRequest,
                                                       @RequestHeader(name = "platform") String platform) {
    Map<String, String> headers = ServerHeaderExtractor.extractHeadersAsMap(servletRequest);
    headerValidator.validate(headers, DefaultHeaders.class);

    return ResponseEntity.ok(tokenManagementService.getToken(headers, Platform.parse(platform)));
  }

  @DeleteMapping
  public ResponseEntity<?> cleanToken(HttpServletRequest servletRequest,
                                      @RequestHeader(name = "platform") String platform) {
    Map<String, String> headers = ServerHeaderExtractor.extractHeadersAsMap(servletRequest);
    headerValidator.validate(headers, DefaultHeaders.class);

    tokenManagementService.cleanToken(headers, Platform.parse(platform));
    return ResponseEntity.noContent().build();
  }
}
