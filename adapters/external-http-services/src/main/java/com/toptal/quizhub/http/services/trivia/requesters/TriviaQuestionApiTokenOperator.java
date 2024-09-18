package com.toptal.quizhub.http.services.trivia.requesters;

import com.toptal.quizhub.commons.constants.Communications;
import com.toptal.quizhub.commons.constants.Systems;
import com.toptal.quizhub.commons.utils.LoggingUtils;
import com.toptal.quizhub.http.services.trivia.TriviaApi;
import com.toptal.quizhub.http.services.trivia.TriviaServiceContainer;
import com.toptal.quizhub.ports.http.services.api.question.QuestionApiTokenOperator;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class TriviaQuestionApiTokenOperator implements QuestionApiTokenOperator {

    private final TriviaServiceContainer triviaServiceContainer;

    private final TriviaRequester triviaRequester;

    @Override
    public String resetToken(String token) {
        final Supplier<String> supplier =
                () -> {
                    final TriviaApi triviaApi = triviaServiceContainer.getTriviaApi();

                    return triviaRequester.resetToken(triviaApi, token);
                };

        return encapsulateWithLogging(supplier);
    }

    @Override
    public String retrieveToken() {
        final Supplier<String> supplier =
                () -> {
                    final TriviaApi triviaApi = triviaServiceContainer.getTriviaApi();

                    return triviaRequester.retrieveToken(triviaApi);
                };

        return encapsulateWithLogging(supplier);
    }

    private String encapsulateWithLogging(Supplier<String> supplier) {

        return LoggingUtils.encapsulateWithLogContext(Systems.TRIVIA,
                Communications.CommunicationTypeEnum.HTTP_OUT.getCommunicationTypeValue(),
                supplier);
    }
}
