package com.toptal.quizhub.persistence.jpa.exceptions;

public class NoCustomSortFoundException extends RuntimeException {

    private static final long serialVersionUID = 5886326629904101377L;

    private NoCustomSortFoundException(String message) {

        super(message);
    }

    public static NoCustomSortFoundException of(String entity, String field) {

        return new NoCustomSortFoundException(String.format("Custom sort field not found for entity [%s] with field [%s]", entity, field));
    }
}
