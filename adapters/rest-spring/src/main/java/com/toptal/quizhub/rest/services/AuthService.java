package com.toptal.quizhub.rest.services;

import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.domain.users.AuthUserUseCase;
import com.toptal.quizhub.rest.converters.users.UserConverter;
import com.toptal.quizhub.rest.converters.users.sort.UserSignUpResponseDtoConverter;
import com.toptal.quizhub.rest.requests.authentication.UserSignUpRequestDTO;
import com.toptal.quizhub.rest.responses.authentication.UserSignUpResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserUseCase authUserUseCase;

    private final UserConverter userConverter;
    private final UserSignUpResponseDtoConverter userSignUpResponseDtoConverter;

    public UserSignUpResponseDTO register(UserSignUpRequestDTO userSignUpRequestDTO) {
        final User user = authUserUseCase.register(userConverter.convert(userSignUpRequestDTO));
        return userSignUpResponseDtoConverter.convert(user);
    }

    public void logout() {

        authUserUseCase.logout();
    }
}
