package com.toptal.quizhub.telemetry.api;

import com.toptal.quizhub.telemetry.api.tags.MeterTag;
import com.toptal.quizhub.telemetry.api.types.CounterType;

import java.util.Set;

public interface CounterIncrementer {

    void increment(CounterType counterType, Set<MeterTag> tags);
}
