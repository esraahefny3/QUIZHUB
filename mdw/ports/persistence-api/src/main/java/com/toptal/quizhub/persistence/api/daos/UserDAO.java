package com.toptal.quizhub.persistence.api.daos;

import com.toptal.quizhub.domain.catalog.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDAO {

    Optional<User> findBySid(UUID sid);

    Optional<User> findByEmail(String email);

    User insert(User user);

}
