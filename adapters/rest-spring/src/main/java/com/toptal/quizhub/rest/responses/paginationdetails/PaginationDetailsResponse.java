package com.toptal.quizhub.rest.responses.paginationdetails;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class PaginationDetailsResponse {

    long totalCount;

    Integer itemsPerPage;

    long totalPages;
}

