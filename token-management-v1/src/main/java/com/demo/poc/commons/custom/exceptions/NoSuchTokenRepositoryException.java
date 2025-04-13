package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class NoSuchTokenRepositoryException extends GenericException {

    public NoSuchTokenRepositoryException() {
        super(ErrorDictionary.NO_SUCH_TOKEN_REPOSITORY.getMessage(), ErrorDictionary.parse(NoSuchTokenRepositoryException.class));
    }
}
