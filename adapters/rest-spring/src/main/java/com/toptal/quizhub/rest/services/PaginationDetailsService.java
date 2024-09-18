package com.toptal.quizhub.rest.services;

import com.toptal.quizhub.rest.responses.paginationdetails.PaginationDetailsResponse;

public interface PaginationDetailsService<T> {

    PaginationDetailsResponse getPaginationDetails(T paramObject);
}
