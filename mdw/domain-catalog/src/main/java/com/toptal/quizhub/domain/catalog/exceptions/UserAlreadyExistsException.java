package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class UserAlreadyExistsException extends InvalidInputException {


    public static final String MESSAGE_USER_ALREADY_EXISTS = "User already registred in the system";

    public UserAlreadyExistsException() {

        super(ErrorCode.USER_ALREADY_EXISTS);
    }

    private UserAlreadyExistsException(ErrorCode errorCode, String message) {

        super(errorCode, message);
    }

    public static UserAlreadyExistsException useAlreadyExistsException() {

        return new UserAlreadyExistsException(ErrorCode.USER_ALREADY_EXISTS,
                MESSAGE_USER_ALREADY_EXISTS);
    }
}
