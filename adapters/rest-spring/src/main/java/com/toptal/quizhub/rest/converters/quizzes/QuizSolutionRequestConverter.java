package com.toptal.quizhub.rest.converters.quizzes;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Question;
import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.QuizSolution;
import com.toptal.quizhub.rest.requests.quizzes.QuizSolutionRequestDTO;
import com.toptal.quizhub.rest.responses.quizzes.GetQuizResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizSolutionRequestConverter extends Converter <QuizSolutionRequestDTO, QuizSolution> {


    @Mapping(target = "questionSolutions" , source = "questionSolutions")
    QuizSolution convert(QuizSolutionRequestDTO source);



}

