package com.toptal.quizhub.rest.converters.questions;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshRequest;
import com.toptal.quizhub.rest.requests.questions.QuestionsRefreshRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionsRefreshRequestConverter extends Converter<QuestionsRefreshRequestDTO, QuestionsRefreshRequest> {

    QuestionsRefreshRequest convert(QuestionsRefreshRequestDTO source);

}

