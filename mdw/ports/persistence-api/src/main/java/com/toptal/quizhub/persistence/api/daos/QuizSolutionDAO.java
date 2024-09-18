package com.toptal.quizhub.persistence.api.daos;

import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.QuizSolution;
import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.domain.catalog.User;

import java.util.Optional;
import java.util.UUID;

public interface QuizSolutionDAO {

    Optional<QuizSolutionCatalog> findBy(Quiz quiz,User user);

    Optional<QuizSolutionCatalog> save(QuizSolution quizSolution,Quiz quiz, String userEmail);

}
