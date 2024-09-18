package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class MissingMandatoryFieldException extends InvalidInputException {

    private MissingMandatoryFieldException(String message) {

        super(ErrorCode.MISSING_MANDATORY_FIELD, message);
    }

    public static MissingMandatoryFieldException forField(
            String fieldName) {

        return new MissingMandatoryFieldException(String.format("[%s] is mandatory", fieldName));
    }
}
