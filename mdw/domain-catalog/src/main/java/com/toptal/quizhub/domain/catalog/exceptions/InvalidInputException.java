package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.exceptions.ErrorCoded;

public abstract class InvalidInputException extends CodedException {

    protected InvalidInputException(ErrorCoded errorCoded) {

        super(errorCoded);
    }

    protected InvalidInputException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }

    protected InvalidInputException(ErrorCoded errorCoded, Throwable throwable) {

        super(errorCoded, throwable);
    }

    protected InvalidInputException(ErrorCoded errorCoded, String message, Throwable throwable) {

        super(errorCoded, message, throwable);
    }
}
