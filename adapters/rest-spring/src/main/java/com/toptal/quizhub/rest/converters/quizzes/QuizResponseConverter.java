package com.toptal.quizhub.rest.converters.quizzes;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.rest.requests.quizzes.QuizCreationRequestDTO;
import com.toptal.quizhub.rest.responses.quizzes.QuizCreationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizResponseConverter extends Converter<Quiz, QuizCreationResponse> {

    QuizCreationResponse convert(Quiz source);

}

