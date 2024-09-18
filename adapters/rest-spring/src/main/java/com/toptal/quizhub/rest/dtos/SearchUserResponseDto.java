package com.toptal.quizhub.rest.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchUserResponseDto {

    Long idUser;

    String fullName;

    String username;

    Boolean visible;
}
