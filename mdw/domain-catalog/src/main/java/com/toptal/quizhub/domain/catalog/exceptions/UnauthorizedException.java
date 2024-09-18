package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class UnauthorizedException extends CodedException {

    private static final long serialVersionUID = 7433551117221333654L;

    protected UnauthorizedException(ErrorCoded errorCoded) {

        super(errorCoded);
    }

    protected UnauthorizedException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }

    public UnauthorizedException(Throwable throwable) {

        super(ErrorCode.UNAUTHORIZED, throwable);
    }

    public UnauthorizedException() {

        super(ErrorCode.UNAUTHORIZED);
    }
}
