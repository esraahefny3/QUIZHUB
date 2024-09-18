package com.toptal.quizhub.commons.providers;

import java.time.Instant;

public class InstantProvider {

    public Instant getNow() {

        return Instant.now();
    }
}
