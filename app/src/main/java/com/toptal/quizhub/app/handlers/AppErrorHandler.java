package com.toptal.quizhub.app.handlers;

import com.toptal.quizhub.rest.exceptions.errors.ErrorCode;
import com.toptal.quizhub.rest.handlers.ErrorResponseFactory;
import com.toptal.quizhub.rest.responses.errors.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppErrorHandler {

    @ExceptionHandler(ConcurrencyFailureException.class)
    protected ResponseEntity<ErrorResponse> handleConcurrencyFailureException(ConcurrencyFailureException exception) {

        log.error("Concurrency Failure exception", exception);
        return new ResponseEntity<>(ErrorResponseFactory.create(ErrorCode.CONCURRENT_UPDATE_DETECTED, "A concurrent update was detected"),
                HttpStatus.CONFLICT);
    }
}
