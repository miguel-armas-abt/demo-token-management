package com.demo.poc.entrypoint.management.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class NoSuchTokenRepositoryException extends GenericException {

    private static final String EXCEPTION_CODE = "01.01.05";
    
    public NoSuchTokenRepositoryException() {
        super(
            EXCEPTION_CODE,
            "No such token repository",
            INTERNAL_SERVER_ERROR
        );
    }
}
