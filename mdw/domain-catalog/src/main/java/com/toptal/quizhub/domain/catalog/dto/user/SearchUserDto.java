package com.toptal.quizhub.domain.catalog.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchUserDto {

    Long idUser;

    String fullName;

    String username;

    String email;

    Boolean visible;
}
