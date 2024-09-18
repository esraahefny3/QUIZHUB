package com.toptal.quizhub.http.services.trivia.requesters;

import com.toptal.quizhub.commons.constants.Communications;
import com.toptal.quizhub.commons.constants.Systems;
import com.toptal.quizhub.commons.utils.LoggingUtils;
import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshCatalog;
import com.toptal.quizhub.http.services.trivia.TriviaApi;
import com.toptal.quizhub.http.services.trivia.TriviaServiceContainer;
import com.toptal.quizhub.ports.http.services.api.question.QuestionsFetcher;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class TriviaQuestionsFetcher implements QuestionsFetcher {

    private final TriviaServiceContainer triviaServiceContainer;

    private final TriviaRequester triviaRequester;

    @Override
    public List<CachedQuestion> fetch(QuestionsRefreshCatalog questionsRefreshCatalog) {
        final Supplier<List<CachedQuestion>> supplier =
                () -> {
                    final TriviaApi triviaApi = triviaServiceContainer.getTriviaApi();

                    return triviaRequester.fetchQuestions(triviaApi, questionsRefreshCatalog);
                };

        return encapsulateWithLogging(supplier);
    }

    private List<CachedQuestion> encapsulateWithLogging(Supplier<List<CachedQuestion>> supplier) {

        return LoggingUtils.encapsulateWithLogContext(Systems.TRIVIA,
                Communications.CommunicationTypeEnum.HTTP_OUT.getCommunicationTypeValue(),
                supplier);
    }
}
