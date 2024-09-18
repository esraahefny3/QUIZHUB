package com.toptal.quizhub.rest.converters.paginations;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.rest.configurations.PaginationConfiguration;
import com.toptal.quizhub.rest.requests.pagination.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PageRequestConverter implements Converter<Pageable, PageRequest> {

    private final PaginationConfiguration paginationConfiguration;

    @Override
    public PageRequest convert(Pageable source) {

        return convert(source.getLimit(), source.getOffset());
    }

    public PageRequest convert(Integer limit, Integer offset) {

        final int actualOffset = Optional.ofNullable(offset).orElse(0);
        final int actualLimit = Optional.ofNullable(limit)
                .map(sourceLimit -> Math.min(sourceLimit, paginationConfiguration.getMaxLimit()))
                .orElse(paginationConfiguration.getDefaultLimit());
        return PageRequest.of(actualOffset, actualLimit);
    }

    public PageRequest convertExportItems(Integer limit, Integer offset) {

        final int actualOffset = Optional.ofNullable(offset).orElse(0);
        final int actualLimit = Optional.ofNullable(limit)
                .map(sourceLimit -> Math.min(sourceLimit, paginationConfiguration.getMaxExportLimit()))
                .orElse(paginationConfiguration.getMaxExportLimit());

        return PageRequest.of(actualOffset, actualLimit);
    }
}
