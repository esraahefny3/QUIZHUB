package com.toptal.quizhub.rest.converters.paginations.sort;

import com.toptal.quizhub.rest.exceptions.SortConvertException;
import com.toptal.quizhub.rest.requests.pagination.sort.Field;
import com.toptal.quizhub.rest.requests.pagination.sort.Sort;
import com.toptal.quizhub.rest.requests.pagination.sort.Sortable;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RestSortConverterTest {

    private final RestSortConverter subject = new RestSortConverter();

    @Test
    void convert_whenHasBlankField_throwsException() {

        // Given
        final String sortString = "+field1,  ,-field2";

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.convert(sortString);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(SortConvertException.class);
    }

    @Test
    void convert_whenHasUnknownDirection_throwsException() {

        // Given
        final String sortString = "?field1,-field2";

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.convert(sortString);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(SortConvertException.class);
    }

    @Test
    void convert_whenIsValidStringWithSpaces_returnsSortable() {

        //Given
        final String sortString = "   +field1    ,    -field2    ";

        //When
        final Sortable sortable = subject.convert(sortString);

        //Then
        assertThat(sortable)
                .isEqualTo(Sort.by(Field.of("field1", Sortable.Direction.ASC), Field.of("field2", Sortable.Direction.DESC)));
    }

    @Test
    void convert_whenIsValidString_returnsSortable() {

        //Given
        final String sortString = "+field1,-field2";

        //When
        final Sortable sortable = subject.convert(sortString);

        //Then
        assertThat(sortable)
                .isEqualTo(Sort.by(Field.of("field1", Sortable.Direction.ASC), Field.of("field2", Sortable.Direction.DESC)));
    }
}
