package com.toptal.quizhub.http.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toptal.quizhub.domain.catalog.exceptions.ResponseBodyMissingException;
import com.toptal.quizhub.ports.http.services.api.exceptions.ErrorCode;
import com.toptal.quizhub.ports.http.services.api.exceptions.HttpException;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import org.slf4j.Logger;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public abstract class HttpRequester {

    protected <T> Optional<T> getOptionalResponse(Call<T> call) {

        try {

            final UUID correlationId = UUID.randomUUID();

            getLogger().debug("Request: {}, correlation_id: {}",
                    getRequestString(call.request()), correlationId);
            final Response<T> response = call.execute();

            if (response.isSuccessful()) {
                getLogger().debug("Successful Response: {}, correlation_id: {}",
                        response, correlationId);
                return Optional.ofNullable(response.body());
            }

            final int responseCode = response.code();
            try (ResponseBody responseBody = response.errorBody()) {

                final String errorBody = responseBody != null ? responseBody.string() : "";
                getLogger().error("Error Response: {}, Http Code: {}, correlation_id: {}",
                        errorBody, responseCode, correlationId);
                throw getHttpException(responseCode, errorBody);
            }

        } catch (IOException ioException) {
            throw HttpException.builder()
                    .httpCode(ErrorCode.UNEXPECTED_ERROR.getCode())
                    .errorCoded(ErrorCode.UNEXPECTED_ERROR)
                    .throwable(ioException)
                    .build();
        }
    }

    protected <T> T getResponse(Call<T> call) {

        return getOptionalResponse(call)
                .orElseThrow(() -> ResponseBodyMissingException.forRequest(call.request().toString()));
    }

    protected abstract Logger getLogger();

    private String getRequestString(Request request) throws IOException {

        String body = null;

        if (request.body() != null) {
            try (BufferedSink sink = new Buffer()) {
                request.body().writeTo(sink.buffer());
                body = sink.toString();
            }
        }

        return "Request{method="
                + request.method()
                + ", url="
                + request.url()
                + ", body="
                + body
                + '}';
    }

    public abstract HttpException getHttpException(int responseCode, String errorBody) throws JsonProcessingException;
}
