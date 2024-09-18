package com.toptal.quizhub.app.exceptions;

import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    TENANT_HEADER_NOT_FOUND(1),
    INVALID_TENANT_HEADER(2),
    ERROR_WHILE_RUNNING_FILTER(3);

    private final int code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.APP + code;
    }

    @Override
    public String getName() {

        return name();
    }
}
