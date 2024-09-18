package com.toptal.quizhub.persistence.jpa.daos.impl;

import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.persistence.api.daos.UserDAO;
import com.toptal.quizhub.persistence.jpa.converters.users.UserPersistenceConverter;
import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import com.toptal.quizhub.persistence.jpa.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    private final UserPersistenceConverter userPersistenceConverter;


    @Override
    public Optional<User> findBySid(UUID sid) {

        return userRepository.findById(sid).map(userPersistenceConverter::convert);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userPersistenceConverter::convert);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User insert(User user) {

        final UserEntity persisted = persistUser(user);

        return userPersistenceConverter.convert(persisted);
    }

    private UserEntity persistUser(User user) {

        final Instant now = Instant.now();
        final UserEntity userEntity = userPersistenceConverter.convertToSource(user);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);

        return userRepository.saveAndFlush(userEntity);
    }
}
