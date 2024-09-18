package com.toptal.quizhub.auth.helpers;

import com.toptal.quizhub.auth.PasswordEncoderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderHelperImpl implements PasswordEncoderHelper {
    private final PasswordEncoder passwordEncoder;

    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

