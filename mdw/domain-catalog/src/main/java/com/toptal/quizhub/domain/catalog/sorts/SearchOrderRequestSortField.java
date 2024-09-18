package com.toptal.quizhub.domain.catalog.sorts;

import com.toptal.quizhub.domain.catalog.exceptions.InvalidSortException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SearchOrderRequestSortField implements NamedField {
    ORDER_NUMBER("orderNumber"),
    CREATED_AT("createdAt");

    private final String name;

    public static SearchOrderRequestSortField of(String stringField) {

        final String searchString = stringField.strip();
        return Arrays.stream(values())
                .filter(sortField -> sortField.name.equals(searchString))
                .findAny()
                .orElseThrow(() -> InvalidSortException.forField(stringField));
    }
}
