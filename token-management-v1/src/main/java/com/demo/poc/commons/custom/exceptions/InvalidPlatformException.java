package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class InvalidPlatformException extends GenericException {

    public InvalidPlatformException() {
        super(ErrorDictionary.INVALID_PLATFORM.getMessage(), ErrorDictionary.parse(InvalidPlatformException.class));
    }
}
