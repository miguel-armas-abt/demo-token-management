package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class NoSuchTokenCacheHelperException extends GenericException {

    public NoSuchTokenCacheHelperException() {
        super(ErrorDictionary.TOKEN_APP_RESPONSE_NULL.getMessage(), ErrorDictionary.parse(NoSuchTokenCacheHelperException.class));
    }
}
