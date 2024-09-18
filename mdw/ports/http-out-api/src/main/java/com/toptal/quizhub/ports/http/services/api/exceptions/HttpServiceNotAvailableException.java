package com.toptal.quizhub.ports.http.services.api.exceptions;

public class HttpServiceNotAvailableException extends HttpException {

    public static final int SERVICE_NOT_AVAILABLE = 503;

    public HttpServiceNotAvailableException(String errorResponseBody, String message) {

        super(SERVICE_NOT_AVAILABLE, ErrorCode.SERVICE_NOT_AVAILABLE, errorResponseBody, message);
    }
}
