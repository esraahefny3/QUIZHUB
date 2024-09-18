package com.toptal.quizhub.commons.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class JsonToStringFailedException extends CodedException {

    public JsonToStringFailedException() {

        super(ErrorCode.JSON_TO_STRING_FAILED, "Json can't be converted to String.");
    }
}
