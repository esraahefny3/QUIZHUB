package com.toptal.quizhub.http.services.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.http.services.exceptions.errors.ErrorCode;


public class InvalidParameterException extends CodedException {

    private InvalidParameterException(String message) {

        super(ErrorCode.INVALID_PARAMETER, message);
    }

    public static InvalidParameterException forParams() {
        return new InvalidParameterException("Contains an invalid parameter. Arguements passed in aren't valid.");
    }


}
