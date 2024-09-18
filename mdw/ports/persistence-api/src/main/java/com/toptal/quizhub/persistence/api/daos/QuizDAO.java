package com.toptal.quizhub.persistence.api.daos;

import com.toptal.quizhub.domain.catalog.Quiz;

import java.util.Optional;
import java.util.UUID;

public interface QuizDAO {
 

    Quiz insert(Quiz user);

    Quiz updateQuiz(Quiz updatedQuiz);
    void deleteQuiz(UUID quizExternalId);

    Optional<Quiz> findByExternalId(UUID externalId);

}
