package com.toptal.quizhub.app.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.InternalErrorException;

public class FilterException extends InternalErrorException {

    public FilterException(Throwable throwable) {

        super(ErrorCode.ERROR_WHILE_RUNNING_FILTER, throwable);
    }
}
