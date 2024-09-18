package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpNotAcceptableException extends HttpException {

    public static final int NOT_ACCEPTABLE = 406;

    public HttpNotAcceptableException(String errorResponseBody) {

        super(NOT_ACCEPTABLE, ErrorCode.NOT_ACCEPTABLE, errorResponseBody);
    }

    public HttpNotAcceptableException(String errorResponseBody, String message) {

        super(NOT_ACCEPTABLE, ErrorCode.NOT_ACCEPTABLE, errorResponseBody, message);
    }
}
