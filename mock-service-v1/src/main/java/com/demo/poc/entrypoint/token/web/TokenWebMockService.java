package com.demo.poc.entrypoint.token.web;

import java.util.concurrent.TimeUnit;

import com.demo.poc.commons.config.MockService;
import org.apache.http.entity.ContentType;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpStatusCode;

import org.springframework.stereotype.Component;

import static com.demo.poc.commons.utils.DelayGenerator.generateRandomDelay;
import static com.demo.poc.commons.utils.HeadersGenerator.contentType;
import static com.demo.poc.commons.utils.HeadersGenerator.generateTraceId;
import static com.demo.poc.commons.utils.JsonReader.readJSON;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Component
public class TokenWebMockService implements MockService {

  @Override
  public void loadMocks(ClientAndServer mockServer) {

    mockServer
        .when(request()
            .withMethod("POST")
            .withPath("/poc/web/security/v1/token"))
        .respond(request -> {

          long randomDelay = generateRandomDelay();
          Header traceIdHeader = generateTraceId();

          return response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withHeader(contentType(ContentType.APPLICATION_JSON.getMimeType()))
              .withHeader(traceIdHeader)
              .withBody(readJSON("mocks/web-security-v1/web-token.200.json"))
              .withDelay(TimeUnit.MILLISECONDS, randomDelay);
        });
  }
}
