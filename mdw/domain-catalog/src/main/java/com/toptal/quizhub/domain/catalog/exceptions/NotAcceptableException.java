package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.exceptions.ErrorCoded;

public class NotAcceptableException extends CodedException {

    public NotAcceptableException(ErrorCoded errorCode, String message, Throwable throwable) {

        super(errorCode, message, throwable);
    }

    public NotAcceptableException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }
}
