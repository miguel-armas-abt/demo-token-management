package com.demo.poc.entrypoint.search.rest;

import com.demo.poc.commons.custom.enums.Platform;
import com.demo.poc.entrypoint.search.helper.TokenSearchHelper;
import com.demo.poc.entrypoint.search.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/token-management/v1/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenSearchRestService {

  private final TokenSearchHelper tokenSearchHelper;

  @GetMapping
  public ResponseEntity<TokenResponseWrapper> getToken(@RequestHeader(name = "platform") String platform) {
    return ResponseEntity.ok(tokenSearchHelper.getToken(Platform.parse(platform)));
  }
}
