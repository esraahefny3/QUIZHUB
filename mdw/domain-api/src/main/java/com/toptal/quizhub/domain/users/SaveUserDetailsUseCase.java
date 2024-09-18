package com.toptal.quizhub.domain.users;

import com.toptal.quizhub.domain.catalog.User;

import java.util.Optional;
import java.util.UUID;

public interface SaveUserDetailsUseCase {

    Optional<User> getBySid(UUID uuid);

    Optional<User> getByEmail(String email);
}
