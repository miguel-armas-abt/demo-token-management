package com.demo.service.entrypoint.management.repository.impl;

import java.util.Map;

import com.demo.commons.errors.dto.ErrorDto;
import com.demo.commons.properties.restclient.RestClient;
import com.demo.commons.restclient.RestClientTemplate;
import com.demo.service.commons.properties.ApplicationProperties;
import com.demo.service.entrypoint.management.enums.Platform;
import com.demo.service.entrypoint.management.repository.TokenRepository;
import com.demo.service.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Repository
@RequiredArgsConstructor
public class TokenAppRepositoryImpl implements TokenRepository {

    private static final String SERVICE_NAME = "app-security-v1";

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
        return Platform.APP.equals(platform);
    }
}
