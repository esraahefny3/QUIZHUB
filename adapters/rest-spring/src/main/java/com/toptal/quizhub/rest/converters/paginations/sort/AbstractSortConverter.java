package com.toptal.quizhub.rest.converters.paginations.sort;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.sorts.NamedField;
import com.toptal.quizhub.domain.catalog.sorts.SortRequest;
import com.toptal.quizhub.rest.requests.pagination.sort.Sortable;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractSortConverter<T extends NamedField> implements Converter<Sortable, SortRequest<T>> {

    @Override
    public SortRequest<T> convert(Sortable sort) {

        if (sort == null || CollectionUtils.isEmpty(sort.getSortFields())) {
            return Optional.ofNullable(getDefaultSort())
                    .map(sortRequest -> SortRequest.by(getDefaultSort()))
                    .orElse(SortRequest.empty());
        }

        final List<SortRequest.FieldSortRequest<T>> sortFields = sort.getSortFields()
                .stream()
                .map(this::convertSortField)
                .collect(Collectors.toList());

        return SortRequest.by(sortFields);
    }

    protected abstract SortRequest.FieldSortRequest<T> convertSortField(Sortable.SortField field);

    protected abstract SortRequest.FieldSortRequest<T> getDefaultSort();
}
