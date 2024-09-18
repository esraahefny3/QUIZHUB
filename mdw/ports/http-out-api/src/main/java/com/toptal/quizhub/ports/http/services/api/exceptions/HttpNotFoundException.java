package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpNotFoundException extends HttpException {

    public static final int NOT_FOUND = 404;

    public HttpNotFoundException(String errorResponseBody) {

        super(NOT_FOUND, ErrorCode.NOT_FOUND, errorResponseBody);
    }

    public HttpNotFoundException(String errorResponseBody, String message) {

        super(NOT_FOUND, ErrorCode.NOT_FOUND, errorResponseBody, message);
    }
}
