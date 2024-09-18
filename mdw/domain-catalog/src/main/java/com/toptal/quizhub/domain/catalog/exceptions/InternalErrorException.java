package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.exceptions.ErrorCoded;

public abstract class InternalErrorException extends CodedException {

    public InternalErrorException(ErrorCoded errorCode, Throwable throwable) {

        super(errorCode, throwable);
    }

    public InternalErrorException(ErrorCoded errorCode, String message, Throwable throwable) {

        super(errorCode, message, throwable);
    }

    public InternalErrorException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }
}
