package com.toptal.quizhub.rest.handlers;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import com.toptal.quizhub.commons.validations.exceptions.Error;
import com.toptal.quizhub.rest.exceptions.errors.ErrorCode;
import com.toptal.quizhub.rest.responses.errors.ErrorResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ErrorResponseFactoryTest {

    @Test
    void create_withErrorsAndExtraInfo_returnErrorResponseWithErrors() {

        // Given
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;
        final String message = "Message";
        final Set<Error> errors = Set.of(new Error("field1", "message1", "error1"),
                new Error("field2", "message2", "error2"));
        final Object extraInfo = Map.of("extra", "info");

        // When
        final ErrorResponse errorResponse = ErrorResponseFactory.create(errorCode, message, errors, extraInfo);

        // Then
        final ErrorResponse expected = ErrorResponse.builder()
                .code(errorCode.getCode())
                .codeName(errorCode.getName())
                .message(message)
                .errors(errors)
                .extraInfo(extraInfo)
                .build();
        assertThat(errorResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void create_withException_returnErrorResponseWithErrorCodeAndMessage() {

        // Given
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;
        final String message = "Message";

        // When
        final ErrorResponse errorResponse = ErrorResponseFactory.create(new TestException(errorCode, message));

        // Then
        final ErrorResponse expected = ErrorResponse.builder()
                .code(errorCode.getCode())
                .codeName(errorCode.getName())
                .message(errorCode.getName() + " - " + message)
                .build();
        assertThat(errorResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private static class TestException extends CodedException {

        TestException(ErrorCoded errorCode, String message) {

            super(errorCode, message);
        }
    }
}
