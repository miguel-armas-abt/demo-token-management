package com.demo.poc.entrypoint.management.enums;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import com.demo.poc.commons.properties.cache.TimeToLive;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClockSkew {

  FIVE_SECONDS(Duration.ofSeconds(5)),
  FIFTEEN_SECONDS(Duration.ofSeconds(15)),
  HALF_MINUTE(Duration.ofSeconds(30));

  private final Duration clockSkew;

  public static Duration getTtlWithClockSkew(TokenResponseWrapper tokenResponse, TimeToLive timeToLive) {
    return Optional.ofNullable(tokenResponse.getExpiresIn())
        .map(Long::parseLong)
        .map(Duration::ofSeconds)
        .map(ttl -> {
          Duration clockSkew = Arrays.stream(ClockSkew.values())
              .filter(skew -> ttl.compareTo(skew.getClockSkew()) > 0)
              .min(Comparator.comparing(ClockSkew::getClockSkew))
              .map(ClockSkew::getClockSkew)
              .orElse(Duration.ZERO);

          return  (ttl.compareTo(clockSkew) > 0) ? ttl.minus(clockSkew) : ttl;
        })
        .orElseGet(() -> TimeToLive.getTtl(timeToLive));
  }
}