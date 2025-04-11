package com.demo.poc.commons.custom.exceptions;

import java.util.Arrays;

import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorDictionary {

    //system=00
    INVALID_FIELD("01.00.01", "Invalid field", BAD_REQUEST, InvalidFieldException.class),
    NO_SUCH_REST_CLIENT("01.00.02", "No such rest client", INTERNAL_SERVER_ERROR, NoSuchRestClientException.class),

    //custom=01
    TOKEN_APP_RESPONSE_NULL("01.01.01", "Token APP response is null", CONFLICT, TokenAppResponseNullException.class),
    NO_SUCH_TOKEN_REPOSITORY("01.01.02", "No such token repository", INTERNAL_SERVER_ERROR, NoSuchTokenRepositoryException.class),
    INVALID_PLATFORM("01.01.03", "Invalid platform", BAD_REQUEST, InvalidPlatformException.class);

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
