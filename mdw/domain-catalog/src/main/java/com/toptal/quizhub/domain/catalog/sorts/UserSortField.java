package com.toptal.quizhub.domain.catalog.sorts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSortField implements NamedField {
    INSERT_ORDER("insert_order"),
    USERNAME("username");

    private final String name;
}
