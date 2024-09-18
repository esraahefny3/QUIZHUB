package com.toptal.quizhub.persistence.api.performers;

import com.toptal.quizhub.domain.catalog.User;

public interface Inserter<T> {

    T insert(T entity, User createdBy);
}
