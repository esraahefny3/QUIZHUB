package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.exceptions.ErrorCoded;

public abstract class ResourceNotFoundException extends CodedException {

    public ResourceNotFoundException(ErrorCoded errorCode, String message, Throwable throwable) {

        super(errorCode, message, throwable);
    }

    public ResourceNotFoundException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }
}
