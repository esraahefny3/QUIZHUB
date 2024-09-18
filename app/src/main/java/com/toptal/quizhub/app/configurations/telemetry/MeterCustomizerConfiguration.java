package com.toptal.quizhub.app.configurations.telemetry;

import com.toptal.quizhub.telemetry.api.tags.ApplicationTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeterCustomizerConfiguration {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {

        return registry -> {
            final ApplicationTag applicationTag = new ApplicationTag();
            registry.config().commonTags(Tags.of(applicationTag.getKey(), applicationTag.getValue()));
        };
    }
}
