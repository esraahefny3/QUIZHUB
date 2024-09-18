package com.toptal.quizhub.rest.converters.quizzes;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Question;
import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.rest.responses.quizzes.GetQuizResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GetQuizRequestConverter extends Converter <Quiz,GetQuizResponse> {


    @Mapping(target = "questions" , source = "questions")
    GetQuizResponse convert(Quiz source);


    GetQuizResponse.GetQuizQuestionResponse map(Question src);


    List<GetQuizResponse.GetQuizQuestionResponse> map(List<Question> src);

}

