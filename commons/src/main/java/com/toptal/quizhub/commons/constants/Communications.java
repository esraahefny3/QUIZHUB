package com.toptal.quizhub.commons.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Communications {

    public static final String HTTP_IN = CommunicationTypeEnum.HTTP_IN.getCommunicationTypeValue();

    @Getter
    @RequiredArgsConstructor
    public enum CommunicationTypeEnum {
        HTTP_IN("http-in"),
        HTTP_OUT("http-out");

        private final String communicationTypeValue;

    }
}
