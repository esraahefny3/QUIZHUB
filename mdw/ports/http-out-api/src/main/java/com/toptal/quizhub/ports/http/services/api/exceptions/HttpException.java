package com.toptal.quizhub.ports.http.services.api.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.exceptions.ErrorCoded;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

@Getter
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class HttpException extends CodedException {

    private final Integer httpCode;

    private final String errorResponseBody;


    public HttpException(int httpCode, ErrorCoded errorCoded, String errorResponseBody, String message) {

        super(errorCoded, message);
        this.httpCode = httpCode;
        this.errorResponseBody = errorResponseBody;
    }

    public HttpException(int httpCode, ErrorCoded errorCoded, String errorResponseBody) {

        super(errorCoded);
        this.httpCode = httpCode;
        this.errorResponseBody = errorResponseBody;
    }

    @lombok.Builder
    public HttpException(int httpCode,
                         ErrorCoded errorCoded,
                         Throwable throwable,
                         String errorResponseBody,
                         String message) {

        super(errorCoded, message, throwable);
        this.httpCode = httpCode;
        this.errorResponseBody = errorResponseBody;
    }

}
