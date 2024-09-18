package com.toptal.quizhub.rest.handlers;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.validations.exceptions.ConstraintValidationException;
import com.toptal.quizhub.commons.validations.exceptions.Error;
import com.toptal.quizhub.domain.catalog.exceptions.ForbiddenException;
import com.toptal.quizhub.domain.catalog.exceptions.InternalErrorException;
import com.toptal.quizhub.domain.catalog.exceptions.InvalidInputException;
import com.toptal.quizhub.domain.catalog.exceptions.NotAcceptableException;
import com.toptal.quizhub.domain.catalog.exceptions.ResourceNotFoundException;
import com.toptal.quizhub.domain.catalog.exceptions.UnauthorizedException;
import com.toptal.quizhub.rest.exceptions.errors.ErrorCode;
import com.toptal.quizhub.rest.responses.errors.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestErrorHandler {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException exception) {

        log.warn("Constraint validation", exception);
        final Collection<Error> errors = getErrorsFromViolations(exception);
        return new ResponseEntity<>(ErrorResponseFactory.createForInvalidParameters(errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CodedException.class)
    protected ResponseEntity<ErrorResponse> handleCodedException(CodedException exception) {

        log.error("Unhandled exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintValidationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintValidationException(ConstraintValidationException exception) {

        log.warn("Constraint validation", exception);
        final Collection<Error> errors = exception.getErrors();
        return new ResponseEntity<>(ErrorResponseFactory.createForInvalidParameters(errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {

        log.warn("Constraint violation", exception);
        final Set<Error> errors = getErrorsFromViolations(exception);
        return new ResponseEntity<>(ErrorResponseFactory.createForInvalidParameters(errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception) {

        log.error("Unexpected exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(ErrorCode.UNEXPECTED_ERROR, exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ErrorResponse> handleForbiddenException(CodedException exception) {

        log.warn("Forbidden exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(exception), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        log.warn("Http message not readable", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(ErrorCode.INVALID_PARAMS, "Request body is missing or invalid"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleInternalErrorException(InternalErrorException exception) {

        log.error("Internal server error exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException exception) {

        log.warn("Bad request", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {

        log.warn("Bad request", exception);
        return new ResponseEntity<>(ErrorResponseFactory.createForInvalidParameters(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {

        log.warn("Not found exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {

        log.error("Method type mismatch exception", exception);
        final String message = String
                .format("Unable to convert value %s to type %s",
                        exception.getValue(),
                        exception.getParameter().getParameterType());
        return new ResponseEntity<>(ErrorResponseFactory.create(ErrorCode.INVALID_INPUT_TYPE, message),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception) {

        log.warn("Unauthorized exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(exception), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotAcceptableException.class)
    protected ResponseEntity<ErrorResponse> handleNotAcceptableException(NotAcceptableException exception) {

        log.warn("Not acceptable exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(exception), HttpStatus.NOT_ACCEPTABLE);
    }

    private Set<Error> getErrorsFromViolations(BindException exception) {

        return exception.getFieldErrors().stream()
                .map(error -> new Error(error.getField(), error.getDefaultMessage(), error.getRejectedValue()))
                .collect(Collectors.toSet());
    }

    private Set<Error> getErrorsFromViolations(ConstraintViolationException exception) {

        return exception.getConstraintViolations().stream()
                .map(error -> new Error(error.getPropertyPath().toString(), error.getMessage(), error.getInvalidValue()))
                .collect(Collectors.toSet());
    }
}
