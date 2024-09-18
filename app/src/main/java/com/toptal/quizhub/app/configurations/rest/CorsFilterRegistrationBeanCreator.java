package com.toptal.quizhub.app.configurations.rest;

import com.toptal.quizhub.app.configurations.rest.properties.CorsProperties;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Map;

@Component
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class CorsFilterRegistrationBeanCreator {

    private final CorsProperties corsProperties;

    public FilterRegistrationBean<CorsFilter> create() {

        final FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean = new FilterRegistrationBean<>(getCorsFilter());
        corsFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsFilterFilterRegistrationBean;
    }

    private CorsConfiguration getCorsConfiguration() {

        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(corsProperties.getAllowedHeaders());
        config.setAllowedOrigins(corsProperties.getAllowedOrigins());
        config.setAllowedMethods(corsProperties.getAllowedMethods());
        config.setMaxAge(corsProperties.getMaxAge().getSeconds());
        return config;
    }

    private CorsFilter getCorsFilter() {

        return new CorsFilter(getUrlBasedCorsConfigurationSource());
    }

    private UrlBasedCorsConfigurationSource getUrlBasedCorsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.setCorsConfigurations(Map.of(corsProperties.getWebMapping(), getCorsConfiguration()));
        return source;
    }
}
