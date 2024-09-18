package com.toptal.quizhub.ports.http.services.api.exceptions;

import com.toptal.quizhub.commons.converters.Converter;

public class ExceptionConverter implements Converter.WithTwoSources<Integer, String, HttpException>,
        Converter.WithThreeSources<Integer, String, String, HttpException> {

    public static final int SERVICE_NOT_AVAILABLE = 503;

    public static final int INTERNAL_ERROR = 500;

    public static final int CONFLICT = 409;

    public static final int NOT_ACCEPTABLE = 406;

    public static final int NOT_FOUND = 404;

    public static final int FORBIDDEN = 403;

    public static final int UNAUTHORIZED = 401;

    public static final int BAD_REQUEST = 400;


    @Override
    public HttpException convert(Integer httpCode, String errorResponse) {

        return switch (httpCode) {
            case BAD_REQUEST -> new HttpBadRequestException(errorResponse);

            case UNAUTHORIZED -> new HttpUnauthorizedException(errorResponse);

            case FORBIDDEN -> new HttpForbiddenException(errorResponse);

            case NOT_FOUND -> new HttpNotFoundException(errorResponse);

            case INTERNAL_ERROR -> new HttpInternalServerErrorException(errorResponse);

            case SERVICE_NOT_AVAILABLE -> new HttpServiceNotAvailableException(errorResponse,
                    "Service not available, please try again later.");

            case NOT_ACCEPTABLE -> new HttpNotAcceptableException(errorResponse);

            case CONFLICT -> new HttpConflictException(errorResponse);

            default -> new HttpException(httpCode, ErrorCode.UNEXPECTED_ERROR, errorResponse);
        };
    }

    // Added to get error response message sent from Trivia
    @Override
    public HttpException convert(Integer httpCode, String errorResponse, String message) {

        return switch (httpCode) {
            case BAD_REQUEST -> new HttpBadRequestException(errorResponse, message);

            case UNAUTHORIZED -> new HttpUnauthorizedException(errorResponse, message);

            case FORBIDDEN -> new HttpForbiddenException(errorResponse, message);

            case NOT_FOUND -> new HttpNotFoundException(errorResponse, message);

            case INTERNAL_ERROR -> new HttpInternalServerErrorException(errorResponse, message);

            case SERVICE_NOT_AVAILABLE -> new HttpServiceNotAvailableException(errorResponse,
                    "Service not available, please try again later.");

            case NOT_ACCEPTABLE -> new HttpNotAcceptableException(errorResponse, message);

            case CONFLICT -> new HttpConflictException(errorResponse, message);

            default -> new HttpException(httpCode, ErrorCode.UNEXPECTED_ERROR, errorResponse, message);
        };
    }
}
