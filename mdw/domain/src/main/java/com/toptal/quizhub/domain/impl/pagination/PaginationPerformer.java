package com.toptal.quizhub.domain.impl.pagination;

import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.domain.catalog.results.PagedResult;

import java.util.List;
import java.util.function.Function;

public class PaginationPerformer {

    public <T, E> PagedResult<T> getPagedResult(PageRequest originalPageRequest,
                                                Function<PageRequest, List<E>> entityProvider,
                                                Function<E, T> converterFunction) {

        final int extraLimit = originalPageRequest.getLimit() + 1;
        final PageRequest pageRequestWithExtraResult = PageRequest.of(originalPageRequest.getOffset(), extraLimit);

        final List<E> entities = entityProvider.apply(pageRequestWithExtraResult);

        final boolean hasNext = removeExtraElementIfHasNext(extraLimit, entities);

        final List<T> results = entities.stream()
                .map(converterFunction)
                .toList();

        return PagedResult.of(results, hasNext);
    }

    public <T, E> PagedResult<T> getPagedResult(PageRequest originalPageRequest,
                                                Function<PageRequest, List<E>> entityProvider
                                                ) {

        final int extraLimit = originalPageRequest.getLimit() + 1;
        final PageRequest pageRequestWithExtraResult = PageRequest.of(originalPageRequest.getOffset(), extraLimit);

        final List<E> entities = entityProvider.apply(pageRequestWithExtraResult);

        final boolean hasNext = removeExtraElementIfHasNext(extraLimit, entities);

        return (PagedResult<T>) PagedResult.of(entities, hasNext);
    }

    private boolean removeExtraElementIfHasNext(int extraLimit, List<?> entities) {

        final boolean hasNext = entities.size() == extraLimit;
        if (hasNext) {
            entities.remove(extraLimit - 1);
        }
        return hasNext;
    }
}
