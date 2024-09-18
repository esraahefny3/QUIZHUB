package com.toptal.quizhub.app.handlers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AppErrorHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class AppErrorHandlerTest {

    @SpyBean
    private TestController testController;

    @Autowired
    private MockMvc mvc;

    @Test
    void perform_whenConcurrentFailureExceptionIsThrown_returnsBadRequest() throws Exception {

        //Given
        final MockHttpServletRequestBuilder requestBuilder = post("/test-rest-error-handler");

        testController.setSupplier(() -> {
            throw new TestConcurrentFailureException();
        });

        //When
        final ResultActions perform = mvc.perform(requestBuilder);

        //Then
        perform.andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("A concurrent update was detected")));
    }

    @Primary
    @SpringBootConfiguration
    @ComponentScan(basePackages = "com.toptal.quizhub.app.handlers")
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

    private static class TestConcurrentFailureException extends ConcurrencyFailureException {

        TestConcurrentFailureException() {

            super("Error");
        }
    }
}
