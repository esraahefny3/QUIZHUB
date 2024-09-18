package com.toptal.quizhub.app.configurations.http.services;

import com.toptal.quizhub.domain.impl.pagination.PaginationPerformer;
import com.toptal.quizhub.http.services.ApiBuilder;
import com.toptal.quizhub.ports.http.services.api.exceptions.ExceptionConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HttpServiceCommonConfiguration {

    @Bean
    ApiBuilder apiBuilder() {

        return new ApiBuilder();
    }

    @Bean
    ExceptionConverter exceptionConverter() {

        return new ExceptionConverter();
    }

    @Bean
    PaginationPerformer paginationPerformer() {

        return new PaginationPerformer();
    }
}
