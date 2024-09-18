package com.toptal.quizhub.domain.catalog.dto.user;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SystemUserDto {

    private String name;

    private String username;

    private String email;

    private String token;

    private UUID sid;

    private String googleId;
}
