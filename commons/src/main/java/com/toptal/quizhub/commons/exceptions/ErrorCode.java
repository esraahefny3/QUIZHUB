package com.toptal.quizhub.commons.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {

    JSON_TO_STRING_FAILED(0),
    STRING_TO_JSON_FAILED(1);

    private final Integer code;

    @Override
    public Integer getCode() {

        return BaseCodes.COMMONS + this.code;
    }

    @Override
    public String getName() {

        return this.name();
    }
}
