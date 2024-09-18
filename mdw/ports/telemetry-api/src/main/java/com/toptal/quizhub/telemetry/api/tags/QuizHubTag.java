package com.toptal.quizhub.telemetry.api.tags;

import lombok.Value;

@Value(staticConstructor = "of")
public class QuizHubTag implements MeterTag {

    String value;

    @Override
    public String getKey() {

        return "quizhub";
    }
}
