package com.toptal.quizhub.rest.requests.pagination.sort;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Field implements Sortable.SortField {

    @NotNull
    String field;

    Sortable.Direction direction;

    public static Field asc(String field) {

        return of(field, Sortable.Direction.ASC);
    }

    public static Field desc(String field) {

        return of(field, Sortable.Direction.DESC);
    }

    @Override
    public String toString() {

        return getField() + "," + getDirection().name();
    }
}
