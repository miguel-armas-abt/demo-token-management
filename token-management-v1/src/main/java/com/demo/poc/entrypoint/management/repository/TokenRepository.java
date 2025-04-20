package com.demo.poc.entrypoint.management.repository;

import java.util.Map;

import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;

public interface TokenRepository {

    TokenResponseWrapper getToken(Map<String, String> headers);

    boolean supports(Platform platform);
}
