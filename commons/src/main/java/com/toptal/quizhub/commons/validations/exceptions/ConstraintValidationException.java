package com.toptal.quizhub.commons.validations.exceptions;

import com.toptal.quizhub.commons.exceptions.CodedException;
import com.toptal.quizhub.commons.validations.ValidationErrorCode;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

import java.util.Collection;

@Getter
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class ConstraintValidationException extends CodedException {

    private static final long serialVersionUID = 8816061848321265898L;

    private final Collection<Error> errors;

    public ConstraintValidationException(ValidationErrorCode errorCode, String message, Collection<Error> errors) {

        super(errorCode, message);
        this.errors = errors;
    }
}
