package com.toptal.quizhub.persistence.jpa.repositories;

import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.persistence.jpa.entities.QuizResultEntity;
import com.toptal.quizhub.persistence.jpa.repositories.jpa.QuizSolutionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizSolutionRepository extends QuizSolutionJpaRepository {

    Optional<QuizResultEntity> findByQuizExternalIdAndUserSid(UUID quiz, UUID user);
}
