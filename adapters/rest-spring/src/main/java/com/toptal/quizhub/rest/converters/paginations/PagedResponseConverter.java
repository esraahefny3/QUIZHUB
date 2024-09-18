package com.toptal.quizhub.rest.converters.paginations;

import com.toptal.quizhub.domain.catalog.results.PagedResult;
import com.toptal.quizhub.rest.responses.pagination.PageResponse;
import com.toptal.quizhub.rest.responses.pagination.PagedResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PagedResponseConverter {

    public <T, R> PagedResponse<R> convert(PagedResult<T> source, Function<T, R> converter) {

        return new PagedResponse<>(convertResults(source, converter),
                PageResponse.of(source.hasNext()));
    }

    private <T, R> List<R> convertResults(PagedResult<T> source, Function<T, R> converter) {

        return source.getResults()
                .stream()
                .map(converter)
                .toList();
    }
}
