package com.toptal.quizhub.rest.requests.pagination.sort;

import lombok.Value;

@Value
public class FieldRequest implements Sortable.SortField {

    String field;

    Sortable.Direction direction;
}
