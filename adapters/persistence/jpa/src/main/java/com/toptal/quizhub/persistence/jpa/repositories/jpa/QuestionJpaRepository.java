package com.toptal.quizhub.persistence.jpa.repositories.jpa;

import com.toptal.quizhub.persistence.jpa.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface QuestionJpaRepository extends JpaRepository<QuestionEntity,Long> {

    Optional<QuestionEntity> findByExternalId(UUID externalId);

}
