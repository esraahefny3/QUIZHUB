package com.toptal.quizhub.rest.requests.authentication;

import lombok.Value;

@Value
public class UserLoginRequestDTO {

    String email;

    String password;

}
