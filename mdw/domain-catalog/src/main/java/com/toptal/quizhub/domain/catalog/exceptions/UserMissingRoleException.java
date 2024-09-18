package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

public class UserMissingRoleException extends InvalidInputException {


    public static final String MESSAGE_USER_MISSING_ROLE = "User missing role. User should have at least one role";

    public UserMissingRoleException() {

        super(ErrorCode.USER_MISSING_ROLE);
    }

    private UserMissingRoleException(ErrorCode errorCode, String message) {

        super(errorCode, message);
    }

    public static UserMissingRoleException userMissingRoleException() {

        return new UserMissingRoleException(ErrorCode.USER_MISSING_ROLE,
                MESSAGE_USER_MISSING_ROLE);
    }
}
