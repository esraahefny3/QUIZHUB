package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpConflictException extends HttpException {

    public static final int CONFLICT = 409;

    public HttpConflictException(String errorResponseBody) {

        super(CONFLICT, ErrorCode.CONFLICT, errorResponseBody);
    }

    public HttpConflictException(String errorResponseBody, String message) {

        super(CONFLICT, ErrorCode.CONFLICT, errorResponseBody, message);
    }
}
