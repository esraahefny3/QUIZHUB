package com.toptal.quizhub.rest.requests.pagination.sort;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortByFieldsConstraintValidator implements ConstraintValidator<SortByFields, Sortable> {

    private Set<String> availableFields;

    @Override
    public void initialize(SortByFields sortByFields) {

        availableFields = Stream.of(sortByFields.value())
                .map(String::toLowerCase)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean isValid(Sortable sortField, ConstraintValidatorContext cxt) {

        if (sortField == null) {
            return true;
        }

        return sortField.getSortFields().stream()
                .allMatch(f -> validateField(f.getField()));
    }

    private boolean validateField(String field) {

        return field != null && !field.isBlank() && availableFields.contains(field.toLowerCase());
    }
}
