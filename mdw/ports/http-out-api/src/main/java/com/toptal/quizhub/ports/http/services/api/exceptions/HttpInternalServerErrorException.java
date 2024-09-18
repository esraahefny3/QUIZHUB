package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpInternalServerErrorException extends HttpException {

    public static final int INTERNAL_ERROR = 500;

    public HttpInternalServerErrorException(String errorResponseBody) {

        super(INTERNAL_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, errorResponseBody);
    }

    public HttpInternalServerErrorException(String errorResponseBody, String message) {

        super(INTERNAL_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, errorResponseBody, message);
    }
}
