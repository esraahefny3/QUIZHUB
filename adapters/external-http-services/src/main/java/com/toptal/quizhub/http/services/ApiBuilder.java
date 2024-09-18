package com.toptal.quizhub.http.services;

import retrofit2.Retrofit;

public class ApiBuilder {

    public <T> T buildApi(Retrofit.Builder retrofitBuilder, Class<T> tClass) {

        return retrofitBuilder
                .build()
                .create(tClass);
    }

    public <T> T buildApi(String host, Retrofit.Builder retrofitBuilder, Class<T> tClass) {

        return retrofitBuilder
                .baseUrl(host)
                .build()
                .create(tClass);
    }
}
