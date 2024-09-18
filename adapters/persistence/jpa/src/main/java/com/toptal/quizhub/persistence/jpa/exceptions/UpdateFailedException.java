package com.toptal.quizhub.persistence.jpa.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class UpdateFailedException extends CodedException {

    public UpdateFailedException(String message) {

        super(ErrorCode.DATABASE_OPERATION_FAILED, message);
    }
}
