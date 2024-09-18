package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

import java.util.UUID;

public class QuizAlreadyPublishedException extends NotAcceptableException {

    private QuizAlreadyPublishedException(String message) {

        super(ErrorCode.QUIZ_ALREADY_PUBLISHED, message);
    }

    public static QuizAlreadyPublishedException forExternalId(UUID externalId) {

        return new QuizAlreadyPublishedException(
                String.format("Quiz with externalId [%s] is already in status published", externalId));
    }

}
