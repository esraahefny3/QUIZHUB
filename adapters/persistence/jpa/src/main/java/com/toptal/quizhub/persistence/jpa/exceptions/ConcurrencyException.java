package com.toptal.quizhub.persistence.jpa.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.persistence.jpa.exceptions.error.ErrorCode;

public class ConcurrencyException extends CodedException {

    private ConcurrencyException(String message) {

        super(ErrorCode.CONCURRENT_UPDATE_DETECTED, message);
    }

    public static ConcurrencyException forSid(String uid) {

        return new ConcurrencyException(String.format("A concurrent update was detected for item with UID [%s]", uid));
    }
}
