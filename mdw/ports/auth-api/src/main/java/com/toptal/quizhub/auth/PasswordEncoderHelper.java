package com.toptal.quizhub.auth;

public interface PasswordEncoderHelper {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}

