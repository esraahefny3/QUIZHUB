package com.toptal.quizhub.app.configurations.telemetry;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import com.toptal.quizhub.commons.utils.ApplicationContextAccessor;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.BaseUnits;
import lombok.RequiredArgsConstructor;
import org.slf4j.Marker;

@RequiredArgsConstructor
class MetricsTurboFilter extends TurboFilter {

    private final MeterRegistry registry;

    private final ApplicationContextAccessor applicationContextAccessor;

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable throwable) {

        // When filter is asked for decision for an isDebugEnabled call or similar test, there is no message (ie format)
        // and no intention to log anything with this call. We will not increment counters and can return immediately and
        // avoid the relatively expensive ThreadLocal access below. See also logbacks Logger.callTurboFilters().
        if (format == null) {
            return FilterReply.NEUTRAL;
        }

        // cannot use logger.isEnabledFor(level), as it would cause a StackOverflowError by calling this filter again!
        if (level.isGreaterOrEqual(logger.getEffectiveLevel())) {
            switch (level.toInt()) {
                case Level.ERROR_INT:
                    count(registry, "error", "Number of error level events that made it to the logs").increment();
                    break;
                case Level.WARN_INT:
                    count(registry, "warn", "Number of warn level events that made it to the logs").increment();
                    break;
                case Level.INFO_INT:
                    count(registry, "info", "Number of info level events that made it to the logs").increment();
                    break;
                case Level.DEBUG_INT:
                    count(registry, "debug", "Number of debug level events that made it to the logs").increment();
                    break;
                case Level.TRACE_INT:
                    count(registry, "trace", "Number of trace level events that made it to the logs").increment();
                    break;
                default:
                    throw new RuntimeException("Log level not configured");
            }
        }

        return FilterReply.NEUTRAL;
    }

    private Counter count(MeterRegistry registry, String error, String meterDescription) {

        final String system = applicationContextAccessor.getSystem().orElse("none");
        final String network = applicationContextAccessor.getNetworkOrDefault();
        final String communicationType = applicationContextAccessor.getCommunication().orElse("none");

        return Counter.builder("logback.events")
                .tags("level", error,
                        "system", system,
                        "network", network,
                        "communication", communicationType)
                .description(meterDescription)
                .baseUnit(BaseUnits.EVENTS)
                .register(registry);
    }
}
