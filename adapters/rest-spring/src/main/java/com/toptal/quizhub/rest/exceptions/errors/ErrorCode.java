package com.toptal.quizhub.rest.exceptions.errors;

import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    INVALID_PARAMS(1),
    UNEXPECTED_ERROR(2),
    INVALID_SORT_ERROR(3),
    INVALID_ENCODING_ERROR(4),
    CONCURRENT_UPDATE_DETECTED(5),
    INVALID_INPUT_TYPE(6),
    CUSTOM_ERROR_1(7);

    private final Integer code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.REST + this.code;
    }

    @Override
    public String getName() {

        return this.name();
    }
}
