package com.toptal.quizhub.http.services.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.http.services.exceptions.errors.ErrorCode;


public class TokenNotValidException extends CodedException {

    private TokenNotValidException(String message) {

        super(ErrorCode.TOKEN_NOT_VALID, message);
    }

    public static TokenNotValidException forError() {
        return new TokenNotValidException(String.format("Token cannot be used in the requested service."
                + " Token Not Found Session Token does not exist."));
    }


}
