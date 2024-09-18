package com.toptal.quizhub.rest.converters.paginations.sort;

import com.toptal.quizhub.rest.exceptions.SortConvertException;
import com.toptal.quizhub.rest.requests.pagination.sort.Field;
import com.toptal.quizhub.rest.requests.pagination.sort.Sort;
import com.toptal.quizhub.rest.requests.pagination.sort.Sortable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RestSortConverter implements Converter<String, Sortable> {

    private static final Sortable.Direction[] DIRECTIONS = Sortable.Direction.values();

    /**
     * Parse a string like: "+field_1, -field_2".
     * It should result in a sortable represented by list of {@link Sortable.SortField}
     * <li>field: field_1; direction: asc</li>
     * <li>field: field_2; direction: desc</li>
     *
     * @param text a string separated by comas - with or without spaces
     * @return a {@link Sortable}
     */
    @Override
    public Sortable convert(final String text) {

        final String[] fields = text.split(",");
        final Set<Sortable.SortField> sortFields = Arrays.stream(fields)
                .peek(field -> validateField(text, field))
                .map(String::trim)
                .map(field -> Field.of(field.substring(1), extractDirection(field)))
                .collect(Collectors.toUnmodifiableSet());

        return Sort.by(sortFields);
    }

    private Sortable.Direction extractDirection(String field) {

        final char fieldDirection = field.trim().charAt(0);

        return Stream.of(DIRECTIONS)
                .filter(direction -> direction.getOperator() == fieldDirection)
                .findFirst()
                .orElseThrow(() -> SortConvertException.forUnknownDirection(fieldDirection));
    }

    private void validateField(String text, String field) {

        if (StringUtils.isBlank(field)) {
            throw SortConvertException.forBlankField(text);
        }
    }
}
