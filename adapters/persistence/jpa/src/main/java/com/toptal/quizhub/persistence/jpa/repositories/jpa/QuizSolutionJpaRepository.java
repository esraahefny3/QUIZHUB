package com.toptal.quizhub.persistence.jpa.repositories.jpa;

import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.persistence.jpa.entities.QuizEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuizResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface QuizSolutionJpaRepository extends JpaRepository<QuizResultEntity,Long> {

    Optional<QuizEntity> findByExternalId(UUID externalId);

    void deleteByExternalId(UUID uuid);

}
