package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class TokenAppResponseNullException extends GenericException {

    public TokenAppResponseNullException() {
        super(ErrorDictionary.TOKEN_APP_RESPONSE_NULL.getMessage(), ErrorDictionary.parse(TokenAppResponseNullException.class));
    }
}
