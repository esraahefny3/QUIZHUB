package com.toptal.quizhub.app.configurations.http.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Component
public class RetrofitBuilderFactory {

    public Retrofit.Builder make(ObjectMapper objectMapper,
                                 HttpOutProperties properties,
                                 String host) {

        return this.make(objectMapper, properties)
                .baseUrl(host);
    }

    public Retrofit.Builder make(ObjectMapper objectMapper,
                                 HttpOutProperties properties) {

        return buildRetroFit(objectMapper,
                properties.getMaxIdleConnections(),
                properties.getKeepAlive(),
                properties.getReadTimeout(),
                properties.getConnectionTimeout());
    }

    private Retrofit.Builder buildRetroFit(ObjectMapper objectMapper,
                                           int maxIdleConnections,
                                           int keepAlive,
                                           int readTimeout,
                                           int connectionTimeout) {

        final ConnectionPool pool = new ConnectionPool(maxIdleConnections, keepAlive, TimeUnit.MILLISECONDS);

        final OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(pool)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
                // TODO :: .addInterceptor()
                .build();

        final JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create(objectMapper);
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(jacksonConverterFactory);
    }
}
