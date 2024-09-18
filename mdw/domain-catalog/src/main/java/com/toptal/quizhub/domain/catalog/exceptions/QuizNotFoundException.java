package com.toptal.quizhub.domain.catalog.exceptions;

import com.toptal.quizhub.domain.catalog.exceptions.errors.ErrorCode;

import java.util.UUID;

public class QuizNotFoundException extends ResourceNotFoundException {

    private QuizNotFoundException(String message) {

        super(ErrorCode.QUIZ_NOT_FOUND, message);
    }

    public static QuizNotFoundException forExternalId(UUID externalId) {

        return new QuizNotFoundException(
                String.format("Quiz with externalId [%s] is not found", externalId));
    }

}
