package com.demo.service.entrypoint.management.repository.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponseWrapper implements Serializable {

    @JsonAlias({"accessToken", "access_token"})
    private String accessToken;

    @JsonAlias({"tokenType", "token_type"})
    private String tokenType;

    private String scope;

    @JsonAlias({"expiresIn", "expires_in"})
    private String expiresIn;

    private String jti;

}