package com.toptal.quizhub.app.configurations.rest.properties;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties("cors")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class CorsProperties {

    public static final Duration DEFAULT_MAX_AGE = Duration.ofSeconds(1800);

    private String webMapping;

    private List<String> allowedOrigins;

    private List<String> allowedMethods;

    private List<String> allowedHeaders;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration maxAge = DEFAULT_MAX_AGE;
}
