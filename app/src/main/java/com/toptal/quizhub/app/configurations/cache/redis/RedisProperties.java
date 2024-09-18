package com.toptal.quizhub.app.configurations.cache.redis;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
@Validated
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class RedisProperties {

    private String host;

    private Integer port;

    private Integer readTimeout;

    private Duration expireDuration;

    private String password;

    private String prefix;
}
