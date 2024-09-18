package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class InvalidSortException extends InvalidInputException {

    private InvalidSortException(String message) {

        super(ErrorCode.INVALID_SORT, message);
    }

    public static InvalidSortException forField(String field) {

        return new InvalidSortException(String.format("Invalid sort for field [%s]", field));
    }
}
