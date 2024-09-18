package com.toptal.quizhub.telemetry.api.tags;

public class ApplicationTag implements MeterTag {

    @Override
    public String getKey() {

        return "application";
    }

    @Override
    public String getValue() {

        return "quizhub";
    }
}
