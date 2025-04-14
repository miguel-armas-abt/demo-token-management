package com.demo.poc.entrypoint.management.rest;

import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import com.demo.poc.entrypoint.management.service.TokenManagementService;
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

  @GetMapping
  public ResponseEntity<TokenResponseWrapper> getToken(@RequestHeader(name = "platform") String platform) {
    return ResponseEntity.ok(tokenManagementService.getToken(Platform.parse(platform)));
  }

  @DeleteMapping
  public ResponseEntity<?> cleanToken(@RequestHeader(name = "platform") String platform) {
    tokenManagementService.cleanToken(Platform.parse(platform));
    return ResponseEntity.noContent().build();
  }
}
