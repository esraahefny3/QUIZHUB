package com.toptal.quizhub.ports.http.services.api.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionConverterTest {

    private final ExceptionConverter subject = new ExceptionConverter();

    @Test
    void convert_when400_returnsBadRequestException() {

        //given
        final int code = 400;

        //Then
        final HttpException httpException = subject.convert(code);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpBadRequestException.class);
    }

    @Test
    void convert_when401_returnsUnauthorizedException() {

        //given
        final int code = 401;

        //Then
        final HttpException httpException = subject.convert(code);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpUnauthorizedException.class);
    }

    @Test
    void convert_when403_returnsForbiddenException() {

        //given
        final int code = 403;

        //Then
        final HttpException httpException = subject.convert(code);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpForbiddenException.class);
    }

    @Test
    void convert_when404_returnsNotFoundException() {

        //given
        final int code = 404;

        //Then
        final HttpException httpException = subject.convert(code);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpNotFoundException.class);
    }

    @Test
    void convert_when406_returnsServiceNotAcceptableException() {

        // Given
        final int code = 406;

        // When
        final HttpException httpException = subject.convert(code);

        // Then
        assertThat(httpException).isExactlyInstanceOf(HttpNotAcceptableException.class);
    }

    @Test
    void convert_when409_returnsHttpConflictException() {

        // Given
        final int code = 409;

        // When
        final HttpException httpException = subject.convert(code);

        // Then
        assertThat(httpException).isExactlyInstanceOf(HttpConflictException.class);
    }

    @Test
    void convert_when500_returnsInternalServerErrorException() {

        //given
        final int code = 500;

        //Then
        final HttpException httpException = subject.convert(code);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpInternalServerErrorException.class);
    }

    @Test
    void convert_when503_returnsServiceNotAvailableException() {

        //given
        final int code = 503;

        //Then
        final HttpException httpException = subject.convert(code);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpServiceNotAvailableException.class);
    }

    @Test
    void convert_whenUnhandled_returnsHttpExceptionWithTheReceivedHttpStatus() {

        //given
        final int code = 501;

        //Then
        final HttpException httpException = subject.convert(code);

        //Then
        assertThat(httpException)
                .isExactlyInstanceOf(HttpException.class)
                .matches(e -> ((HttpException) e).getHttpCode().equals(501));
    }
}
