package com.toptal.quizhub.rest.converters.quizzes;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Question;
import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.rest.requests.quizzes.QuizUpdateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizUpdateRequestConverter extends Converter<QuizUpdateRequestDTO, Quiz> {

    @Mapping(target = "author",ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "questions", source = "quizQuestionRequestDtoList")
    Quiz convert(QuizUpdateRequestDTO source);

    List<Question> map(List<QuizUpdateRequestDTO.QuizQuestionRequestDto> src);

    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Question map(QuizUpdateRequestDTO.QuizQuestionRequestDto src);
}

