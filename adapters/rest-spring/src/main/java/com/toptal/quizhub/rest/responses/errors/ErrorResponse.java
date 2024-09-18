package com.toptal.quizhub.rest.responses.errors;

import com.toptal.quizhub.commons.validations.exceptions.Error;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class ErrorResponse {

    int code;

    String codeName;

    String message;

    Collection<Error> errors;

    Object extraInfo;
}
