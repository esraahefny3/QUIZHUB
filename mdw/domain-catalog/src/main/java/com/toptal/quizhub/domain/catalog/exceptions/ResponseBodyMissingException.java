package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class ResponseBodyMissingException extends ResourceNotFoundException {

    private ResponseBodyMissingException(String message) {

        super(ErrorCode.MISSING_HTTP_RESPONSE, message);
    }

    public static ResponseBodyMissingException forRequest(String request) {

        return new ResponseBodyMissingException(
                String.format("Unexpected Null response for Http request [%s]", request));
    }

}
