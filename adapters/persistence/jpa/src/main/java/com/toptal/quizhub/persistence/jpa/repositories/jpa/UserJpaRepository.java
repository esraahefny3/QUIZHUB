package com.toptal.quizhub.persistence.jpa.repositories.jpa;

import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);
}
