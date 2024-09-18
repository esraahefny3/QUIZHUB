package com.toptal.quizhub.persistence.jpa.repositories.jpa;

import com.toptal.quizhub.persistence.jpa.entities.QuizEntity;
import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface QuizJpaRepository extends JpaRepository<QuizEntity,Long> {

    Optional<QuizEntity> findByExternalId(UUID externalId);

    void deleteByExternalId(UUID uuid);

}
