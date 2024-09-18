package com.toptal.quizhub.rest.requests.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public interface Pageable {

    @Min(value = 1, message = "Limit should not be less than 1")
    @Max(value = 50, message = "Limit should not be grater than 50")
    Integer getLimit();

    @Min(value = 0, message = "Limit should not be less than 0")
    Integer getOffset();
}
