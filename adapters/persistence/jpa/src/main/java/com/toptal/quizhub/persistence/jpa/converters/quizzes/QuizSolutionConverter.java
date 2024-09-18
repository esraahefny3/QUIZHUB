package com.toptal.quizhub.persistence.jpa.converters.quizzes;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Question;
import com.toptal.quizhub.domain.catalog.QuestionAnswer;
import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.persistence.jpa.entities.QuestionAnswerEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuestionEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuizEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuizResultEntity;
import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface QuizSolutionConverter extends Converter<QuizResultEntity, QuizSolutionCatalog> {

    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "user", ignore = true)
    QuizSolutionCatalog convert(QuizResultEntity input);

    @Mapping(target = "question.quiz", ignore = true)
    QuestionAnswer toQuestionAnswer(QuestionAnswerEntity questionsA);
    List<QuestionAnswer> toQuestionEntityList(List<QuestionAnswerEntity> questionsA);

}
