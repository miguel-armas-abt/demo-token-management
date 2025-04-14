package com.demo.poc.entrypoint.management.repository;

import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;

public interface TokenRepository {

    TokenResponseWrapper getToken();

    boolean supports(Platform platform);
}
