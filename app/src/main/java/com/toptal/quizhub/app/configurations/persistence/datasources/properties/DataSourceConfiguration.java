package com.toptal.quizhub.app.configurations.persistence.datasources.properties;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "datasource")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class DataSourceConfiguration {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private Integer maximumPoolSize;

    private Integer minimumIdle;

    private Long idleTimeout;

    private Long connectionTimeout;

    private Long maxLifetime;

    private Integer leakDetectionThreshold;

    private Boolean autoCommit;

    private Map<String, String> extraConfigs = new HashMap<>();

}
