package com.toptal.quizhub.rest.requests.pagination.sort;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.annotation.Annotation;

import static org.mockito.MockitoAnnotations.openMocks;

class SortByFieldsConstraintValidatorTest {

    private final SortByFieldsConstraintValidator subject = new SortByFieldsConstraintValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    public void setUp() {

        openMocks(this);
        subject.initialize(new SortByFieldsTestClass(new String[]{"id"}));
    }

    @Test
    void isValid_whenInvalidValue_returnFalse() {

        //Given
        final Sortable sort = Sort.by(Field.of("invalid", Sortable.Direction.ASC));

        //When
        final boolean valid = subject.isValid(sort, constraintValidatorContext);

        //Then
        Assertions.assertThat(valid).isFalse();
    }

    @Test
    void isValid_whenUpperCaseValues_returnTrue() {

        //Given
        final Sortable sort = Sort.by(Field.of("ID", Sortable.Direction.ASC));

        //When
        final boolean valid = subject.isValid(sort, constraintValidatorContext);

        //Then
        Assertions.assertThat(valid).isTrue();
    }

    @Test
    void isValid_whenValidLowerCaseValue_returnFalse() {

        //Given
        final Sortable sort = Sort.by(Field.of("id", Sortable.Direction.ASC));

        //When
        final boolean valid = subject.isValid(sort, constraintValidatorContext);

        //Then
        Assertions.assertThat(valid).isTrue();
    }

    @SuppressWarnings("ClassExplicitlyAnnotation")
    @RequiredArgsConstructor
    private static class SortByFieldsTestClass implements SortByFields {

        private final String[] fields;

        @Override
        public Class<? extends Annotation> annotationType() {

            return null;
        }

        @Override
        public Class<?>[] groups() {

            return new Class[0];
        }

        @Override
        public String message() {

            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Class<? extends Payload>[] payload() {

            return new Class[0];
        }

        @Override
        public String[] value() {

            return fields;
        }
    }
}
