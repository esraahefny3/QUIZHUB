package com.toptal.quizhub.rest.requests.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UserSignUpRequestDTO {

    @NotBlank
    String username;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;
}
