package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpForbiddenException extends HttpException {

    public static final int FORBIDDEN = 403;

    public HttpForbiddenException(String errorResponseBody) {

        super(FORBIDDEN, ErrorCode.FORBIDDEN, errorResponseBody);
    }

    public HttpForbiddenException(String errorResponseBody, String message) {

        super(FORBIDDEN, ErrorCode.FORBIDDEN, errorResponseBody, message);
    }
}
