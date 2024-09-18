package com.toptal.quizhub.cache.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CacheFactory<T> {

    void put(String key, T value) throws JsonProcessingException;

    T get(String key, Class<?>cls) throws JsonProcessingException;

    void deleteAll(String pattern);

    void delete(String key);

    List<T> getPaginatedValuesByPattern(String pattern, int offset, int limit,Class<?> cls);


}
