package com.toptal.quizhub.persistence.jpa.daos.impl;

import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.QuizSolution;
import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.persistence.api.daos.QuizSolutionDAO;
import com.toptal.quizhub.persistence.api.daos.UserDAO;
import com.toptal.quizhub.persistence.jpa.converters.quizzes.QuizSolutionConverter;
import com.toptal.quizhub.persistence.jpa.converters.users.UserPersistenceConverter;
import com.toptal.quizhub.persistence.jpa.entities.QuestionAnswerEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuizEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuizResultEntity;
import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import com.toptal.quizhub.persistence.jpa.repositories.QuizRepository;
import com.toptal.quizhub.persistence.jpa.repositories.QuizSolutionRepository;
import com.toptal.quizhub.persistence.jpa.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuizSolutionDAOImpl implements QuizSolutionDAO {

    private final QuizSolutionRepository quizSolutionRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizSolutionConverter quizSolutionConverter;

    @Override
    public Optional<QuizSolutionCatalog> findBy(Quiz quiz, User user) {
        Optional<QuizResultEntity> quizResultEntity=quizSolutionRepository.findByQuizExternalIdAndUserSid(quiz.getExternalId(), user.getSid());
        if(quizResultEntity.isPresent())
        {
            return Optional.of(quizSolutionConverter.convert(quizResultEntity.get()));
        }
        else
        {
            return Optional.empty();
        }

    }

    @Override
    public Optional<QuizSolutionCatalog> save(QuizSolution quizSolution, Quiz quiz, String userEmail) {

        QuizEntity quizEntity = quizRepository.findByExternalId(quiz.getExternalId()).orElseThrow(() -> new RuntimeException("Cannot find the quiz"));
        UserEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Cannot find the user"));

        QuizResultEntity quizResultEntity = new QuizResultEntity();
        Instant instant = Instant.now();
        quizResultEntity.setCreatedAt(instant);
        quizResultEntity.setExternalId(UUID.randomUUID());
        quizResultEntity.setUpdatedAt(instant);
        quizResultEntity.setQuiz(quizEntity);
        quizResultEntity.setUser(userEntity);
        List<QuestionAnswerEntity> questionAnswerEntities = new ArrayList<>();

        long totalScore = 0;
        int noOfQuestions=quiz.getQuestions().size();
        for (int i = 0; i < noOfQuestions; i++) {
            int score = 0;
            final UUID extId = quiz.getQuestions().get(i).getExternalId();
            Optional<QuizSolution.QuestionSolution> solution = quizSolution.getQuestionSolutions().stream()
                    .filter(s -> s.getExternalId().equals(extId)).findFirst();
            if(!solution.isPresent())
            {
                //skip
                continue;
            }

            if (quiz.getQuestions().get(i).getCorrectAnswer().equals(solution.get().getAnswer())) {
                totalScore += 1;
                score += 1;
            }
            //create QuestionAnswer
            QuestionAnswerEntity questionAnswerEntity = new QuestionAnswerEntity();
            questionAnswerEntity.setExternalId(UUID.randomUUID());
            questionAnswerEntity.setSelectedAnswer(solution.get().getAnswer());
            questionAnswerEntity.setQuestionScore(score);

            questionAnswerEntity.setCreatedAt(instant);
            questionAnswerEntity.setUpdatedAt(instant);
            questionAnswerEntities.add(questionAnswerEntity);
            quizSolution.getQuestionSolutions().remove(solution);

        }

        quizResultEntity.setQuestionAnswerEntities(questionAnswerEntities);
        quizResultEntity.setScore((long) (((double)totalScore/noOfQuestions)*100));
        return Optional.of(quizSolutionConverter.convert(quizSolutionRepository.save(quizResultEntity)));

    }
}
