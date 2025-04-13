package com.demo.poc.entrypoint.management.repository.impl;

import java.util.Optional;

import com.demo.poc.commons.custom.enums.Platform;
import com.demo.poc.commons.custom.exceptions.TokenAppResponseNullException;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import com.demo.poc.commons.custom.properties.restclient.RestClient;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class TokenWebRepositoryImpl implements TokenRepository {

    private static final String SERVICE_NAME = "web-security-v1";

    private final RestTemplate restTemplate;
    private final ApplicationProperties properties;

    @Override
    public TokenResponseWrapper getToken() {
        RestClient restClient = properties.getRestClients().get(SERVICE_NAME);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(restClient.getHeaders());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.setAll(restClient.getFormData());

        return Optional.ofNullable(restTemplate.exchange(
                restClient.getEndpoint(),
                HttpMethod.POST,
                new HttpEntity<>(requestBody, httpHeaders),
                TokenResponseWrapper.class).
                getBody())
                .orElseThrow(TokenAppResponseNullException::new);
    }

    @Override
    public boolean supports(Platform platform) {
        return Platform.WEB.equals(platform);
    }
}
