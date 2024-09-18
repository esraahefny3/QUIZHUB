package com.toptal.quizhub.app.configurations.system.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SystemProperties {

    @Value("${system.user.google-id}")
    private String systemUserGoogleId;

    @Value("${system.user.auth-token}")
    private String systemUserAuthToken;
}
