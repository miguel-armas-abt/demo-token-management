package com.demo.service.entrypoint.management.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidPlatformException extends GenericException {

    private static final String EXCEPTION_CODE = "01.02.02";

    public InvalidPlatformException() {
        super(
            EXCEPTION_CODE,
            "Invalid platform",
            BAD_REQUEST
        );
    }
}
