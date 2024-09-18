package com.toptal.quizhub.http.services.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.http.services.exceptions.errors.ErrorCode;


public class TokenExhaustedException extends CodedException {

    private TokenExhaustedException(String message) {

        super(ErrorCode.TOKEN_EXHAUSTED, message);
    }

    public static TokenExhaustedException forError() {
        return new TokenExhaustedException(String.format("Token cannot be used in the requested service."
                + "Session Token has returned all possible questions for the specified query."
                + " Resetting the Token is necessary."));
    }


}
