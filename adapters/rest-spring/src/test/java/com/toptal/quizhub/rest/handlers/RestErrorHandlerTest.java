package com.toptal.quizhub.rest.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.validations.ValidationErrorCode;
import com.toptal.quizhub.commons.validations.exceptions.ConstraintValidationException;
import com.toptal.quizhub.domain.catalog.exceptions.ForbiddenException;
import com.toptal.quizhub.domain.catalog.exceptions.InternalErrorException;
import com.toptal.quizhub.domain.catalog.exceptions.InvalidInputException;
import com.toptal.quizhub.domain.catalog.exceptions.ResourceNotFoundException;
import com.toptal.quizhub.domain.catalog.exceptions.UnauthorizedException;
import com.toptal.quizhub.rest.exceptions.errors.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RestErrorHandler.class)
class RestErrorHandlerTest {

    @SpyBean
    private TestController testController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void perform_whenCodedExceptionAndHasExtraInfoIsThrown_returnsInternalError() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new TestWithExtraInfoException(Map.of("extra", "1",
                    "info", "2"));
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.extraInfo.extra", is("1")))
                .andExpect(jsonPath("$.extraInfo.info", is("2")));
    }

    @Test
    void perform_whenCodedExceptionIsThrown_returnsInternalError() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new TestException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isInternalServerError());
    }

    @Test
    void perform_whenConstraintValidationExceptionIsThrown_returnsBadRequest() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new ConstraintValidationException(ValidationErrorCode.VALIDATION_ERROR, "constraint validation", Set.of());
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void perform_whenConstraintViolationExceptionIsThrown_returnsBadRequest() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new ConstraintViolationException("constraint validation", Set.of());
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void perform_whenForbiddenExceptionIsThrown_returnsForbidden() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new ForbiddenException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isForbidden());
    }

    @Test
    void perform_whenHttpMessageNotReadableExceptionIsThrown_returnsBadRequest() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new HttpMessageNotReadableException("Invalid payload", mock(HttpInputMessage.class));
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void perform_whenInternalErrorExceptionIsThrown_returnsInternalError() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new TestInternalErrorException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isInternalServerError());
    }

    @Test
    void perform_whenInvalidInputExceptionIsThrown_returnsBadRequest() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new TestInvalidInputException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void perform_whenMethodArgumentNotValidExceptionIsThrown_returnsBadRequest() throws Exception {

        //Given
        final TestRequest request = TestRequest.builder()
                .testString("")
                .testInteger(0)
                .build();

        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler-with-body")
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON);

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[?(@.field=='testString')].field", hasItem("testString")))
                .andExpect(jsonPath("$.errors.[?(@.field=='testString')].rejected", hasItem("")))
                .andExpect(jsonPath("$.errors.[?(@.field=='testString')].message", hasItem("must not be blank")))
                .andExpect(jsonPath("$.errors.[?(@.field=='testInteger')].field", hasItem("testInteger")))
                .andExpect(jsonPath("$.errors.[?(@.field=='testInteger')].rejected", hasItem(0)))
                .andExpect(jsonPath("$.errors.[?(@.field=='testInteger')].message", hasItem("must be greater than or equal to 1")));
    }

    @Test
    void perform_whenMissingServletRequestParameterExceptionIsThrown_returnsBadRequest() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throwMissingServletRequestParameterException();
            return null;
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void perform_whenResourceNotFoundExceptionIsThrown_returnsNotFoundError() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new TestNotFoundException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isNotFound());
    }

    @Test
    void perform_whenRuntimeExceptionIsThrown_returnsInternalServerError() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new RuntimeException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isInternalServerError());
    }

    @Test
    void perform_whenUnauthorizedExceptionIsThrown_returnsUnauthorized() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new UnauthorizedException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    private void throwMissingServletRequestParameterException() {

        throw new MissingServletRequestParameterException("name", "value");
    }

    @Primary
    @SpringBootConfiguration
    @ComponentScan(basePackages = "com.toptal.quizhub.rest.handlers")
    public static class Config {

    }

    @RestController
    @Setter
    private static class TestController {

        private Supplier<Object> supplier;

        @PostMapping("/test-rest-error-handler")
        public void post() {

            supplier.get();
        }

        @PostMapping("/test-rest-error-handler-with-body")
        public void post(@Valid @RequestBody TestRequest testRequest) {

            supplier.get();
        }
    }

    @Value
    @Builder
    private static class TestRequest {

        @NotBlank
        String testString;

        @Min(1)
        Integer testInteger;
    }

    private static class TestInvalidInputException extends InvalidInputException {

        TestInvalidInputException() {

            super(ErrorCode.INVALID_PARAMS);
        }
    }

    private static class TestNotFoundException extends ResourceNotFoundException {

        TestNotFoundException() {

            super(ErrorCode.UNEXPECTED_ERROR, "");
        }
    }

    private static class TestException extends CodedException {

        TestException() {

            super(ErrorCode.UNEXPECTED_ERROR);
        }
    }

    private static class TestWithExtraInfoException extends CodedException {

        @Getter
        private final Object extraInfo;

        TestWithExtraInfoException(Object extraInfo) {

            super(ErrorCode.UNEXPECTED_ERROR);
            this.extraInfo = extraInfo;
        }
    }

    private static class TestInternalErrorException extends InternalErrorException {

        TestInternalErrorException() {

            super(ErrorCode.UNEXPECTED_ERROR, "Error");
        }
    }
}
