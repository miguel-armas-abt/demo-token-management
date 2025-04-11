package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class NoSuchTokenRepositoryException extends GenericException {

    public NoSuchTokenRepositoryException() {
        super(ErrorDictionary.TOKEN_APP_RESPONSE_NULL.getMessage(), ErrorDictionary.parse(NoSuchTokenRepositoryException.class));
    }
}
