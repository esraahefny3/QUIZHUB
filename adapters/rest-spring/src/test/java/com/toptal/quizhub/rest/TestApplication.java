package com.toptal.quizhub.rest;

import com.toptal.quizhub.rest.converters.paginations.sort.RestSortConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TestApplication {

    @Configuration
    @RequiredArgsConstructor
    public static class WebConfig implements WebMvcConfigurer {

        @Override
        public void addFormatters(FormatterRegistry registry) {

            registry.addConverter(new RestSortConverter());
        }

    }
}
