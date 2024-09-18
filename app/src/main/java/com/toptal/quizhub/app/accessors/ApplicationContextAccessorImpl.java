package com.toptal.quizhub.app.accessors;

import com.toptal.quizhub.commons.utils.ApplicationContextAccessor;
import com.toptal.quizhub.commons.utils.LoggingUtils;

import java.util.Optional;

public class ApplicationContextAccessorImpl implements ApplicationContextAccessor {

    public static final String DEFAULT = "-";

    @Override
    public Optional<String> getCommunication() {

        return Optional.ofNullable(LoggingUtils.getCommunication());
    }

    @Override
    public Optional<String> getNetwork() {
        return Optional.empty();
    }


    @Override
    public String getNetworkOrDefault() {

        return DEFAULT;
    }

    @Override
    public String getNetworkOrFail() {
        return null;
    }

    @Override
    public Optional<String> getSystem() {

        return Optional.ofNullable(LoggingUtils.getSystem());
    }
}
