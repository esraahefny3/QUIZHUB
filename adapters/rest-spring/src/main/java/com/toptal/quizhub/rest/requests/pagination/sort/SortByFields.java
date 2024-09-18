package com.toptal.quizhub.rest.requests.pagination.sort;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SortByFieldsConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(content = @Content(mediaType = "text/plain"), style = ParameterStyle.SIMPLE, example = "insert_order,desc")
public @interface SortByFields {

    Class<?>[] groups() default {};

    String message() default "Invalid sort";

    Class<? extends Payload>[] payload() default {};

    String[] value() default {};
}
