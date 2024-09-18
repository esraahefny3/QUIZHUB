package com.toptal.quizhub.commons.validations;

import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValidationErrorCode implements ErrorCoded {
    VALIDATION_ERROR(1);

    private final Integer code;

    public Integer getCode() {

        return ErrorCoded.BaseCodes.COMMONS + code;
    }

    @Override
    public String getName() {

        return this.name();
    }
}
