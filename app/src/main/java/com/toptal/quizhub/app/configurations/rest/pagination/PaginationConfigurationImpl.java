package com.toptal.quizhub.app.configurations.rest.pagination;

import com.toptal.quizhub.rest.configurations.PaginationConfiguration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PaginationConfigurationImpl implements PaginationConfiguration {

    @Value("${pagination.max-limit}")
    private Integer maxLimit;

    @Value("${pagination.max-export-limit}")
    private Integer maxExportLimit;

    @Value("${pagination.default-limit}")
    private Integer defaultLimit;
}
