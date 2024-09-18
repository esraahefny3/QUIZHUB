package com.toptal.quizhub.persistence.jpa.exceptions.error;

import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    WAREHOUSE_NOT_FOUND(1),
    LOCATION_NOT_FOUND(2),
    BUSINESS_CLIENT_NOT_FOUND(3),
    CONCURRENT_UPDATE_DETECTED(4);

    private final int code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.PERSISTENCE + code;
    }

    @Override
    public String getName() {

        return name();
    }
}
