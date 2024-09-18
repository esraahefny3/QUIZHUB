package com.toptal.quizhub.app.configurations.http.services;

import lombok.Data;

@Data
public abstract class HttpOutProperties {

    private String host;

    private int readTimeout;

    private int connectionTimeout;

    private int maxIdleConnections;

    private int keepAlive;
}
