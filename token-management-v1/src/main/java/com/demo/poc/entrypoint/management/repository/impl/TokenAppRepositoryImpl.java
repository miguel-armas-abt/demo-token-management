package com.demo.poc.entrypoint.management.repository.impl;

import java.util.Map;

import com.demo.poc.commons.core.restclient.RestClientTemplate;
import com.demo.poc.commons.core.restclient.dto.ExchangeRequest;
import com.demo.poc.commons.core.restclient.utils.HeadersFiller;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.commons.custom.properties.restclient.RestClient;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
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
    public TokenResponseWrapper getToken(Map<String, String> headers) {
        RestClient restClient = properties.getRestClients().get(SERVICE_NAME);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.setAll(restClient.getFormData());

        return restTemplate.exchange(
            ExchangeRequest.<MultiValueMap<String, String>, TokenResponseWrapper>builder()
                .url(restClient.getEndpoint())
                .httpMethod(HttpMethod.POST)
                .requestBody(requestBody)
                .responseClass(TokenResponseWrapper.class)
                .headers(HeadersFiller.fillHeaders(restClient.getHeaders(), headers))
                .build(), SERVICE_NAME
        );
    }

    @Override
    public boolean supports(Platform platform) {
        return Platform.APP.equals(platform);
    }
}
