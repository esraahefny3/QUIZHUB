package com.toptal.quizhub.domain.users;

import com.toptal.quizhub.domain.catalog.User;

public interface AuthUserUseCase {

    User register(User user);

    void logout();
}
