package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class GetFromCacheError extends InternalErrorException {


    public static final String MESSAGE = "Error while getting data from cache.";

    private GetFromCacheError(ErrorCode errorCode, String message) {

        super(ErrorCode.GET_FROM_CACHE_ERROR, message);
    }

    public static GetFromCacheError getFromCacheError() {

        return new GetFromCacheError(ErrorCode.USER_ALREADY_EXISTS,
                MESSAGE);
    }
}
