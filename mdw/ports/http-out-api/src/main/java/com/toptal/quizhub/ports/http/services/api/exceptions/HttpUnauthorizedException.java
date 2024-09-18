package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpUnauthorizedException extends HttpException {

    public static final int UNAUTHORIZED = 401;

    public HttpUnauthorizedException(String errorResponseBody) {

        super(UNAUTHORIZED, ErrorCode.UNAUTHORIZED, errorResponseBody);
    }

    public HttpUnauthorizedException(String errorResponseBody, String message) {

        super(UNAUTHORIZED, ErrorCode.UNAUTHORIZED, errorResponseBody, message);
    }
}
