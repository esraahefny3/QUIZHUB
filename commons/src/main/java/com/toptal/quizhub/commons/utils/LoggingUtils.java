package com.toptal.quizhub.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class is responsible to deal with log context for the variables network, system and communicationType.<p/>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingUtils {
    public static final String SYSTEM_CONTEXT_KEY = "system";
    private static final String DEFAULT_VALUE = "default";
    private static final String COMMUNICATION_TYPE_CONTEXT_KEY = "communicationType";
    private static final String LOGGING_CONTEXT_KEY = "logContext";

    public static void clearLoggerContext() {

        MDC.clear();
    }

    public static void encapsulateWithDefaultLogContext(final String communicationType,
                                                        final Runnable runnable) {

        final Map<String, String> contextMap = MDC.getCopyOfContextMap();

        try {
            withContext(getSystem(), communicationType);
            runnable.run();
        } finally {
            Optional.ofNullable(contextMap).ifPresent(MDC::setContextMap);
        }
    }

    public static void encapsulateWithLogContext(final String system,
                                                 final String communicationType,
                                                 final Runnable runnable) {

        final Map<String, String> contextMap = MDC.getCopyOfContextMap();

        try {
            withContext(system, communicationType);
            runnable.run();
        } finally {
            Optional.ofNullable(contextMap).ifPresent(MDC::setContextMap);
        }
    }

    public static <T> T encapsulateWithLogContext(final String system,
                                                  final String communicationType,
                                                  final Supplier<T> supplier) {

        final Map<String, String> contextMap = MDC.getCopyOfContextMap();

        try {
            withContext(system, communicationType);
            return supplier.get();
        } finally {
            Optional.ofNullable(contextMap).ifPresent(MDC::setContextMap);
        }
    }

    public static String getCommunication() {

        return MDC.get(COMMUNICATION_TYPE_CONTEXT_KEY);
    }

    private static String getMDCValueByKeyOrDefault(final String key) {

        return Optional.ofNullable(MDC.get(key)).orElse(DEFAULT_VALUE);
    }

    public static String getSystem() {

        return getMDCValueByKeyOrDefault(SYSTEM_CONTEXT_KEY);
    }

    private static void putInContext(final String contextKey, final String contextValue) {

        Optional.ofNullable(contextValue).map(String::toLowerCase).ifPresent(value -> MDC.put(contextKey, value));
    }

    private static void refreshDefaultLogContext() {

        final String logContext = getMDCValueByKeyOrDefault(COMMUNICATION_TYPE_CONTEXT_KEY)
                .replace('.', '-');

        putInContext(LOGGING_CONTEXT_KEY, logContext);
    }

    private static void refreshLogContext() {

        final String logContext = String.join("-",
                getMDCValueByKeyOrDefault(SYSTEM_CONTEXT_KEY),
                getMDCValueByKeyOrDefault(COMMUNICATION_TYPE_CONTEXT_KEY)
                        .replace('.', '-'));

        putInContext(LOGGING_CONTEXT_KEY, logContext);
    }

    public static void withContext(final String system,
                                   final String communicationType) {

        putInContext(SYSTEM_CONTEXT_KEY, system);
        putInContext(COMMUNICATION_TYPE_CONTEXT_KEY, communicationType);
        refreshLogContext();
    }


}
