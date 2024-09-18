package com.toptal.quizhub.ports.http.services.api.question;

import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshCatalog;

import java.util.List;

public interface QuestionsFetcher {
    String CACHED_QUESTION_PREFIX = "question";

    String REFRESH_QUESTION_PATTERN = CACHED_QUESTION_PREFIX + ".*";

    Integer REQUEST_MAX_AMOUNT = 50;

    String TOKEN_CACHE_KEY = "trivia.token";

    List<CachedQuestion> fetch(QuestionsRefreshCatalog questionsRefreshCatalog);

}
