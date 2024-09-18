package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class SearchStringIsInvalid extends InvalidInputException {

    private SearchStringIsInvalid(String message) {

        super(ErrorCode.INVALID_SEARCH_STRING, message);
    }

    public static SearchStringIsInvalid blank() {

        return new SearchStringIsInvalid("Search string is blank");
    }
}
