package com.toptal.quizhub.rest;

import com.toptal.quizhub.domain.catalog.BaseFaker;
import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.rest.requests.pagination.sort.FieldRequest;
import com.toptal.quizhub.rest.requests.pagination.sort.Sort;
import com.toptal.quizhub.rest.requests.pagination.sort.Sortable;
import com.toptal.quizhub.rest.responses.SidResponse;
import com.toptal.quizhub.rest.responses.pagination.PageResponse;
import com.toptal.quizhub.rest.responses.paginationdetails.PaginationDetailsResponse;

import java.util.UUID;

public class RestFaker extends BaseFaker {

    public PageRequest pageRequest() {

        return PageRequest.of(number().randomDigit(), number().randomDigit());
    }

    public PageResponse pageResponse() {

        return PageResponse.of(bool().bool());
    }

    public PaginationDetailsResponse.Builder paginationDetailsResponse() {

        return PaginationDetailsResponse.builder()
                .totalCount(number().randomNumber())
                .totalPages(number().randomNumber())
                .itemsPerPage(number().randomDigit());
    }

    public SidResponse.Builder sidResponse() {

        return SidResponse.builder()
                .sid(UUID.randomUUID());
    }

    private Sortable sort() {

        return Sort.by(sortField());
    }

    private FieldRequest sortField() {

        return new FieldRequest(animal().name(), randomChoice(Sortable.Direction.values()));
    }
}
