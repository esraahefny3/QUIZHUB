package com.toptal.quizhub.domain.catalog;

import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.domain.catalog.sorts.NamedField;
import com.toptal.quizhub.domain.catalog.sorts.SortRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainFaker extends BaseFaker {

    @SafeVarargs
    public final <T extends NamedField> SortRequest<T> randomSort(T... values) {

        final boolean bool = bool().bool();
        final T field = randomChoice(values);
        return SortRequest.by(
                bool
                        ? SortRequest.FieldSortRequest.asc(field)
                        : SortRequest.FieldSortRequest.desc(field)
        );
    }

    public PageRequest pageRequest() {

        return PageRequest.of(number().numberBetween(0, 50), number().numberBetween(0, 50));
    }

    public <T extends NamedField> SortRequest<T> sortRequest(T field, SortRequest.DirectionRequest directionRequest) {

        return SortRequest.by(SortRequest.FieldSortRequest.by(field, directionRequest));
    }
}
