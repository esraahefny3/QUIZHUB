package com.toptal.quizhub.commons.utils;

import java.util.Optional;

public interface ApplicationContextAccessor {

    Optional<String> getCommunication();

    Optional<String> getNetwork();

    String getNetworkOrDefault();

    String getNetworkOrFail();

    Optional<String> getSystem();
}
