package com.toptal.quizhub.http.services;

import lombok.Value;

@Value(staticConstructor = "of")
public class ServiceWithKey<T> {

    T service;

    String key;
}
