package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class ValueNotFoundException extends InternalErrorException {

    private ValueNotFoundException(String message) {

        super(ErrorCode.VALUE_NOT_FOUND, message);
    }

    public static ValueNotFoundException withMessage(String message) {

        return new ValueNotFoundException(message);
    }
}
