package com.toptal.quizhub.persistence.jpa.converters.quizzes;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Question;
import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.persistence.jpa.entities.QuestionEntity;
import com.toptal.quizhub.persistence.jpa.entities.QuizEntity;
import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface QuizPersistenceConverter extends Converter.BiDirectional<QuizEntity,Quiz> {

    @Mapping(source = "questions", target = "questionEntities")
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "author", target = "userEntity")
    QuizEntity convertToSource(Quiz input);

    List<QuestionEntity> toQuestionEntityList(List<Question> questionsA);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quizEntity", ignore = true)
    QuestionEntity toQuestionEntity(Question questionA);


    @Mapping(target = "author", ignore = true)
    @Mapping(target = "questions", source = "questionEntities")
    Quiz convert(QuizEntity input);

    List<Question> toQuestionList(List<QuestionEntity> questionsA);

    @Mapping(target = "quiz", ignore = true)
    Question toQuestion(QuestionEntity questionA);

    @Mapping(target = "roles", ignore = true)
    UserEntity toUserEntity(User user);


    @Mapping(target = "roles", ignore = true)
    User toUser(UserEntity userEntity);

}
