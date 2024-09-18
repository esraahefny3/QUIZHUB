package com.toptal.quizhub.rest.converters.questions;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshRequest;
import com.toptal.quizhub.rest.requests.questions.QuestionsRefreshRequestDTO;
import com.toptal.quizhub.rest.responses.questions.CachedQuestionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CachedQuestionResponseConverter extends Converter<CachedQuestion, CachedQuestionResponse> {

    CachedQuestionResponse convert(CachedQuestion source);

}

