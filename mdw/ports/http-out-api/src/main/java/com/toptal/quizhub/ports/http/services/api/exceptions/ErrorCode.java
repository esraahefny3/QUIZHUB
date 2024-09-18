package com.toptal.quizhub.ports.http.services.api.exceptions;

import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    BAD_REQUEST(400),
    NOT_FOUND(404),
    NOT_ACCEPTABLE(406),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    SERVICE_NOT_AVAILABLE(503),
    UNEXPECTED_ERROR(0);

    private final Integer code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.EXTERNAL_SERVICES + this.code;
    }

    @Override
    public String getName() {

        return this.name();
    }
}
