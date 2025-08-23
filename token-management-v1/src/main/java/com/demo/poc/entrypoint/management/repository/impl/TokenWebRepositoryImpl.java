package com.demo.poc.entrypoint.management.repository.impl;

import java.util.Map;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.properties.restclient.RestClient;
import com.demo.poc.commons.core.restclient.RestClientTemplate;
import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.commons.properties.ApplicationProperties;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Repository
@RequiredArgsConstructor
public class TokenWebRepositoryImpl implements TokenRepository {

    private static final String SERVICE_NAME = "web-security-v1";

    private final RestClientTemplate restTemplate;
    private final ApplicationProperties properties;

    @Override
    public TokenResponseWrapper generateToken(Map<String, String> headers) {
        RestClient restClient = properties.getRestClients().get(SERVICE_NAME);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.setAll(restClient.getRequest().getFormData());

        return restTemplate
            .service(SERVICE_NAME)
            .post()
            .uri(restClient.getRequest().getEndpoint())
            .headers(headers)
            .body(requestBody)
            .retrieve(TokenResponseWrapper.class, ErrorDto.class);
    }

    @Override
    public boolean supports(Platform platform) {
        return Platform.WEB.equals(platform);
    }
}
