package com.toptal.quizhub.http.services.trivia.converters.questions;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.http.services.trivia.responses.TriviaQuestionsFetchResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CachedQuestionConverter extends Converter<TriviaQuestionsFetchResponse.TriviaQuestionResponse, CachedQuestion> {

    CachedQuestion convert(TriviaQuestionsFetchResponse.TriviaQuestionResponse source);

    List<CachedQuestion> convert(List<TriviaQuestionsFetchResponse.TriviaQuestionResponse> source);
}
