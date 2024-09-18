package com.toptal.quizhub.rest.configurations;

public interface PaginationConfiguration {

    Integer getDefaultLimit();

    Integer getMaxExportLimit();

    Integer getMaxLimit();
}
