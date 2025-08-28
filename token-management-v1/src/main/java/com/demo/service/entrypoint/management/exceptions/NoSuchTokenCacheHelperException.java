package com.demo.service.entrypoint.management.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class NoSuchTokenCacheHelperException extends GenericException {

    private static final String EXCEPTION_CODE = "01.01.06";

    public NoSuchTokenCacheHelperException() {
        super(
            EXCEPTION_CODE,
            "No such token cache helper",
            INTERNAL_SERVER_ERROR
        );
    }
}
