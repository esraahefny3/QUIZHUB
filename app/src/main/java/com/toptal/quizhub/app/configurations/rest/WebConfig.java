package com.toptal.quizhub.app.configurations.rest;

import com.toptal.quizhub.rest.converters.paginations.sort.RestSortConverter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class WebConfig implements WebMvcConfigurer {

    private final CorsFilterRegistrationBeanCreator corsFilterRegistrationBeanCreator;

    private final List<HandlerInterceptor> interceptors;

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new RestSortConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        interceptors.forEach(registry::addInterceptor);
    }


    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {

        return corsFilterRegistrationBeanCreator.create();
    }
}
