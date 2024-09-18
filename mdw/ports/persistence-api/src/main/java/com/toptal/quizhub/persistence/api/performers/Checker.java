package com.toptal.quizhub.persistence.api.performers;

public interface Checker<T> {

    boolean checkIfExists(T entity);
}
