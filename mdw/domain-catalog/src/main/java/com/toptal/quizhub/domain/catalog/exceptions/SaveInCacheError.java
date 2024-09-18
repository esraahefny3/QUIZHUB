package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class SaveInCacheError extends InternalErrorException {


    public static final String MESSAGE = "Error while saving data in cache.";

    private SaveInCacheError(ErrorCode errorCode, String message) {

        super(ErrorCode.SAVE_IN_CACHE_ERROR, message);
    }

    public static SaveInCacheError saveInCacheError() {

        return new SaveInCacheError(ErrorCode.USER_ALREADY_EXISTS,
                MESSAGE);
    }
}
