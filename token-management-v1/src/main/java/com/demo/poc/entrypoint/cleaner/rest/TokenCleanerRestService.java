package com.demo.poc.entrypoint.cleaner.rest;

import com.demo.poc.commons.custom.enums.Platform;
import com.demo.poc.entrypoint.cleaner.helper.TokenCleanerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/token-management/v1/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenCleanerRestService {

  private final TokenCleanerHelper tokenCleanerHelper;

  @DeleteMapping
  public ResponseEntity<?> cleanToken(@RequestHeader(name = "platform") String platform) {
    tokenCleanerHelper.cleanToken(Platform.parse(platform));
    return ResponseEntity.noContent().build();
  }
}
