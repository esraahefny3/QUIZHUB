package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class ForbiddenException extends CodedException {

    public ForbiddenException() {

        super(ErrorCode.FORBIDDEN);
    }
}
