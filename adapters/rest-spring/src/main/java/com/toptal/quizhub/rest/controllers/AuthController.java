package com.toptal.quizhub.rest.controllers;

import com.toptal.quizhub.rest.requests.authentication.UserSignUpRequestDTO;
import com.toptal.quizhub.rest.responses.authentication.UserSignUpResponseDTO;
import com.toptal.quizhub.rest.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public UserSignUpResponseDTO registerUser(@Valid @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        return authService.register(userSignUpRequestDTO);
    }

    @PostMapping("/signout")
    public void logoutUser() {
        authService.logout();
    }
}
