package com.toptal.quizhub.persistence.api.performers;

import com.toptal.quizhub.domain.catalog.User;

public interface Updater<T> {

    T update(T entity, User updatedBy);
}
