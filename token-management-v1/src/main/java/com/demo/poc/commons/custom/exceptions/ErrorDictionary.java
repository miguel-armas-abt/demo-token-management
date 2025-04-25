package com.demo.poc.commons.custom.exceptions;

import java.util.Arrays;

import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchCacheConfigException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchParamMapperException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorDictionary {

    //system=01.00.xx
    INVALID_FIELD("01.00.01", "Invalid field", BAD_REQUEST, InvalidFieldException.class),
    NO_SUCH_REST_CLIENT("01.00.02", "No such rest client", INTERNAL_SERVER_ERROR, NoSuchRestClientException.class),
    NO_SUCH_CACHE_CONFIG("01.00.03", "No such cache config", INTERNAL_SERVER_ERROR, NoSuchCacheConfigException.class),
    NO_SUCH_PARAM_MAPPER("01.00.04", "No such param mapper", BAD_REQUEST, NoSuchParamMapperException.class),

    //token-management=01.01.xx
    NO_SUCH_TOKEN_REPOSITORY("01.01.01", "No such token repository", INTERNAL_SERVER_ERROR, NoSuchTokenRepositoryException.class),
    INVALID_PLATFORM("01.02.02", "Invalid platform", BAD_REQUEST, InvalidPlatformException.class),
    NO_SUCH_TOKEN_CACHE_HELPER("01.01.03", "No such token cache helper", INTERNAL_SERVER_ERROR, NoSuchTokenCacheHelperException.class);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private final Class<? extends GenericException> exceptionClass;

    public static ErrorDictionary parse(Class<? extends GenericException> exceptionClass) {
        return Arrays.stream(ErrorDictionary.values())
                .filter(errorDetail -> errorDetail.getExceptionClass().isAssignableFrom(exceptionClass))
                .findFirst()
                .orElseThrow(() -> new GenericException("No such exception"));
    }
}
