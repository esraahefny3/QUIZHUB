package com.toptal.quizhub.persistence.jpa.daos.impl;

import com.toptal.quizhub.domain.catalog.Question;
import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.persistence.jpa.converters.quizzes.QuizPersistenceConverter;
import com.toptal.quizhub.persistence.jpa.entities.QuestionEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuizEntity;
import com.toptal.quizhub.persistence.jpa.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.toptal.quizhub.persistence.api.daos.QuizDAO;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.toptal.quizhub.persistence.jpa.repositories.QuizRepository;

@Component
@RequiredArgsConstructor
public class QuizDAOImpl implements QuizDAO {

    private final QuizRepository quizJpaRepository;

    private final QuestionRepository questionRepository;

    private final QuizPersistenceConverter quizPersistenceConverter;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<Quiz> findByExternalId(UUID externalId) {

        Optional<QuizEntity> quizEntity= quizJpaRepository.findByExternalId(externalId);
        if(quizEntity.isPresent())
        {
            QuizEntity quizEntity1=quizEntity.get();
            Quiz quiz=quizPersistenceConverter.convert(quizEntity1);
            quiz.setAuthor(quizPersistenceConverter.toUser(quizEntity1.getUserEntity()));
            return Optional.of(quiz);
        }
        else
        {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Quiz insert(Quiz Quiz) {

        final QuizEntity persisted = persistQuiz(Quiz);

        return quizPersistenceConverter.convert(persisted);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Quiz updateQuiz(Quiz updatedQuiz) {
        QuizEntity existingQuiz = quizJpaRepository.findByExternalId(updatedQuiz.getExternalId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // Update the dirty fields
        if(Objects.nonNull(updatedQuiz.getTitle())) {
            existingQuiz.setTitle(updatedQuiz.getTitle());
        }
        if(Objects.nonNull(updatedQuiz.getPublished())) {
            existingQuiz.setPublished(updatedQuiz.getPublished());
        }
        final Instant now = Instant.now();
        if(Objects.nonNull(updatedQuiz.getQuestions())&& !updatedQuiz.getQuestions().isEmpty()) {
            // delete all related questions
            questionRepository.deleteAllById(existingQuiz.getQuestionEntities().stream().map(q->q.getId()).collect(Collectors.toList()));
             updatedQuiz.getQuestions().stream().forEach(q->{
                q.setExternalId(UUID.randomUUID());
                q.setCreatedAt(now);
                q.setUpdatedAt(now);


            });
            existingQuiz.setQuestionEntities(quizPersistenceConverter.toQuestionEntityList(updatedQuiz.getQuestions()));
        }
        existingQuiz.setUpdatedAt(now);

        return quizPersistenceConverter.convert(quizJpaRepository.save(existingQuiz));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteQuiz(UUID quizExternalId) {
        quizJpaRepository.deleteByExternalId((quizExternalId));
    }


    private QuizEntity persistQuiz(Quiz quiz) {

        final Instant now = Instant.now();
        final QuizEntity quizEntity = quizPersistenceConverter.convertToSource(quiz);
        quizEntity.setCreatedAt(now);
        quizEntity.setUpdatedAt(now);
        quizEntity.setExternalId(UUID.randomUUID());
        quizEntity.getQuestionEntities().stream().forEach(q->{
            q.setExternalId(UUID.randomUUID());
            q.setCreatedAt(now);
            q.setUpdatedAt(now);
        });
        return quizJpaRepository.save(quizEntity);
    }


}
