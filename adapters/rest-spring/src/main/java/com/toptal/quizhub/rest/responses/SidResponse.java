package com.toptal.quizhub.rest.responses;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SidResponse {

    UUID sid;
}
