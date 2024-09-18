package com.toptal.quizhub.rest.requests.pagination.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

public interface Sortable {

    Set<SortField> getSortFields();

    @Getter
    @RequiredArgsConstructor
    enum Direction {
        ASC('+'),
        DESC('-');

        private final char operator;
    }

    interface SortField {

        Direction getDirection();

        String getField();
    }
}
