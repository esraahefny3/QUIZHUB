package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpBadRequestException extends HttpException {

    public static final int BAD_REQUEST = 400;

    public HttpBadRequestException(String errorResponseBody) {

        super(BAD_REQUEST, ErrorCode.BAD_REQUEST, errorResponseBody);
    }

    public HttpBadRequestException(String errorResponseBody, String message) {

        super(BAD_REQUEST, ErrorCode.BAD_REQUEST, errorResponseBody, message);
    }
}
