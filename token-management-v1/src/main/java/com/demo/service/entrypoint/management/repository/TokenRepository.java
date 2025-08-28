package com.demo.service.entrypoint.management.repository;

import java.util.Map;

import com.demo.service.entrypoint.management.enums.Platform;
import com.demo.service.entrypoint.management.repository.wrapper.TokenResponseWrapper;

public interface TokenRepository {

    TokenResponseWrapper generateToken(Map<String, String> headers);

    boolean supports(Platform platform);
}
