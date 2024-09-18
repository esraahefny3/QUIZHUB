package com.toptal.quizhub.domain.quizzes;

import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.QuizSolution;
import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;

import java.util.UUID;

public interface QuizUseCase {

    Quiz createQuiz(Quiz quiz, String authorEmail) ;

    Quiz updateQuiz(UUID quizExternalId, Quiz quiz, String userEmail) ;

    void publishQuiz(UUID quizExternalId, String userEmail) ;

    void deleteQuiz(UUID quizExternalId, String userEmail);

    Quiz getQuiz(UUID quizExternalId);

    QuizSolutionCatalog solveQuiz(UUID quizExternalId, QuizSolution quizSolution, String userEmail);

}
