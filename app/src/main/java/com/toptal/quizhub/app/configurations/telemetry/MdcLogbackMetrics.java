package com.toptal.quizhub.app.configurations.telemetry;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import com.toptal.quizhub.commons.utils.ApplicationContextAccessor;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Inspired by {@link io.micrometer.core.instrument.binder.logging.LogbackMetrics}.
 */
public class MdcLogbackMetrics implements MeterBinder, AutoCloseable {

    private final ApplicationContextAccessor applicationContextAccessor;

    private final LoggerContext loggerContext;

    private final Map<MeterRegistry, MetricsTurboFilter> metricsTurboFilters = new HashMap<>();

    public MdcLogbackMetrics(ApplicationContextAccessor applicationContextAccessor) {

        this.applicationContextAccessor = applicationContextAccessor;
        this.loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.addListener(new LoggerContextListenerImpl());
    }

    @Override
    public void bindTo(MeterRegistry registry) {

        final MetricsTurboFilter filter = new MetricsTurboFilter(registry, applicationContextAccessor);
        synchronized (metricsTurboFilters) {
            metricsTurboFilters.put(registry, filter);
            loggerContext.addTurboFilter(filter);
        }
    }

    @Override
    public void close() {

        synchronized (metricsTurboFilters) {
            for (MetricsTurboFilter metricsTurboFilter : metricsTurboFilters.values()) {
                loggerContext.getTurboFilterList().remove(metricsTurboFilter);
            }
        }
    }

    @RequiredArgsConstructor
    private class LoggerContextListenerImpl implements LoggerContextListener {

        @Override
        public boolean isResetResistant() {

            return true;
        }

        @Override
        public void onStart(LoggerContext context) {
            // no-op
        }

        @Override
        public void onReset(LoggerContext context) {

            // re-add turbo filter because reset clears the turbo filter list
            synchronized (metricsTurboFilters) {
                for (MetricsTurboFilter metricsTurboFilter : metricsTurboFilters.values()) {
                    loggerContext.addTurboFilter(metricsTurboFilter);
                }
            }
        }

        @Override
        public void onStop(LoggerContext context) {
            // no-op
        }

        @Override
        public void onLevelChange(Logger logger, Level level) {
            // no-op
        }
    }
}
