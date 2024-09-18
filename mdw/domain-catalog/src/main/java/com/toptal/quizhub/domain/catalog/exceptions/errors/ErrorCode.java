package com.toptal.quizhub.domain.catalog.exceptions.errors;

import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    UNAUTHORIZED(1),
    FORBIDDEN(2),
    INVALID_SORT(3),
    INVALID_SEARCH_STRING(4),
    MISSING_MANDATORY_FIELD(5),
    VALUE_NOT_FOUND(6),

    USER_ALREADY_EXISTS(7),
    MISSING_HTTP_RESPONSE(8),

    DATABASE_OPERATION_FAILED(9),
    USER_MISSING_ROLE(10),
    SAVE_IN_CACHE_ERROR(11),

    GET_FROM_CACHE_ERROR(12),

    QUIZ_NOT_FOUND(13),

    QUIZ_ALREADY_PUBLISHED(13);


    private final int code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.DOMAIN + code;
    }

    @Override
    public String getName() {

        return this.name();
    }
}
