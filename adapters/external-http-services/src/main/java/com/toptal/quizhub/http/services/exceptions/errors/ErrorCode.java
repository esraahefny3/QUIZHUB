package com.toptal.quizhub.http.services.exceptions.errors;

import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    TOKEN_NOT_VALID(1),
    TOKEN_EXHAUSTED(2),
    INVALID_PARAMETER(3);

    private final int code;

    @Override
    public Integer getCode() {

        return BaseCodes.EXTERNAL_HTTP_SERVICES + code;
    }

    @Override
    public String getName() {

        return name();
    }
}
