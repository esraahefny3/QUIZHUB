package com.toptal.quizhub.rest.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.InvalidInputException;
import com.toptal.quizhub.rest.exceptions.errors.ErrorCode;
import lombok.Getter;

@Getter
public class SortConvertException extends InvalidInputException {

    private SortConvertException(String message) {

        super(ErrorCode.INVALID_SORT_ERROR, message);
    }

    public static SortConvertException forBlankField(String fields) {

        return new SortConvertException(String.format("Blank fields found in [%s]", fields));
    }

    public static SortConvertException forUnknownDirection(char direction) {

        return new SortConvertException(String.format("Direction unknown: [%c]", direction));
    }
}
