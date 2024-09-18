package com.toptal.quizhub.domain.questions;

import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshRequest;
import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.domain.catalog.results.PagedResult;

public interface CachedQuestionsUseCase {

    void refreshQuestions(QuestionsRefreshRequest questionsRefreshRequest);

    PagedResult<CachedQuestion> fetchCachedQuestions(String category, String type,String difficulty, PageRequest pageRequest);
}
