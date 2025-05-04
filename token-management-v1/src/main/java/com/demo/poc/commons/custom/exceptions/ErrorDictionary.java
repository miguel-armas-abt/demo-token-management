package com.demo.poc.commons.custom.exceptions;

import java.util.Arrays;

import com.demo.poc.commons.core.errors.exceptions.FileReadException;
import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import com.demo.poc.commons.core.errors.exceptions.JsonReadException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchParamMapperException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientErrorExtractorException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorDictionary {

    //system: 01.00.xx
    ERROR_READING_JSON("01.00.01", "Error reading JSON", INTERNAL_SERVER_ERROR, JsonReadException.class),
    ERROR_READING_FILE("01.00.02", "Error reading JSON", INTERNAL_SERVER_ERROR, FileReadException.class),

    //no such properties and components: 01.01.xx
    NO_SUCH_REST_CLIENT("01.01.01", "No such rest client", INTERNAL_SERVER_ERROR, NoSuchRestClientException.class),
    NO_SUCH_REST_CLIENT_ERROR_EXTRACTOR("01.01.02", "No such rest client error extractor", INTERNAL_SERVER_ERROR, NoSuchRestClientErrorExtractorException.class),
    NO_SUCH_PARAM_MAPPER("01.01.03", "No such param mapper", INTERNAL_SERVER_ERROR, NoSuchParamMapperException.class),
    NO_SUCH_CACHE_CONFIG("01.01.04", "No such cache config", INTERNAL_SERVER_ERROR, NoSuchCacheConfigException.class),
    NO_SUCH_TOKEN_REPOSITORY("01.01.05", "No such token repository", INTERNAL_SERVER_ERROR, NoSuchTokenRepositoryException.class),
    NO_SUCH_TOKEN_CACHE_HELPER("01.01.06", "No such token cache helper", INTERNAL_SERVER_ERROR, NoSuchTokenCacheHelperException.class),

    //business and bad requests: 01.02.xx
    INVALID_FIELD("01.02.01", "Invalid field", BAD_REQUEST, InvalidFieldException.class),
    INVALID_PLATFORM("01.02.02", "Invalid platform", BAD_REQUEST, InvalidPlatformException.class),;

    private final String code;
    private final String message;
    private final HttpStatusCode httpStatus;
    private final Class<? extends GenericException> exceptionClass;

    public static ErrorDictionary parse(Class<? extends GenericException> exceptionClass) {
        return Arrays.stream(ErrorDictionary.values())
            .filter(errorDetail -> errorDetail.getExceptionClass().isAssignableFrom(exceptionClass))
            .findFirst()
            .orElseThrow(() -> new GenericException("No such exception"));
    }
}
