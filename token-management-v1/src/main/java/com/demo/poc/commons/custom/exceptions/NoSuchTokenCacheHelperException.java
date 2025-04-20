package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class NoSuchTokenCacheHelperException extends GenericException {

    public NoSuchTokenCacheHelperException() {
        super(ErrorDictionary.NO_SUCH_TOKEN_CACHE_HELPER.getMessage(), ErrorDictionary.parse(NoSuchTokenCacheHelperException.class));
    }
}
