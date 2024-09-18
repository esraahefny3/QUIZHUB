package com.toptal.quizhub.commons.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class StringToJsonFailedException extends CodedException {

    public StringToJsonFailedException() {

        super(ErrorCode.STRING_TO_JSON_FAILED, "String can't be converted to Json.");
    }
}
