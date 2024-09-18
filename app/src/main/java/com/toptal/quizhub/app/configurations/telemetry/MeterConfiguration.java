package com.toptal.quizhub.app.configurations.telemetry;

import com.toptal.quizhub.commons.utils.ApplicationContextAccessor;
import com.toptal.quizhub.telemetry.api.CounterIncrementer;
import com.toptal.quizhub.telemetry.micrometer.CounterIncrementerImpl;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class MeterConfiguration {

    @Bean
    public CounterIncrementer counterIncrementer(MeterRegistry meterRegistry) {

        return new CounterIncrementerImpl(meterRegistry);
    }

    @Bean
    public MdcLogbackMetrics mdcLogbackMetrics(ApplicationContextAccessor applicationContextAccessor) {

        return new MdcLogbackMetrics(applicationContextAccessor);
    }
}
