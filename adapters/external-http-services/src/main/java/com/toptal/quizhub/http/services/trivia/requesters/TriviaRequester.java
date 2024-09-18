package com.toptal.quizhub.http.services.trivia.requesters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.toptal.quizhub.commons.utils.JsonUtils;
import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshCatalog;
import com.toptal.quizhub.http.services.HttpRequester;
import com.toptal.quizhub.http.services.trivia.TriviaApi;
import com.toptal.quizhub.http.services.trivia.converters.questions.CachedQuestionConverter;
import com.toptal.quizhub.http.services.trivia.handlers.TriviaResponseHandler;
import com.toptal.quizhub.http.services.trivia.responses.TriviaQuestionsFetchResponse;
import com.toptal.quizhub.http.services.trivia.responses.TriviaTokenOperatorResponse;
import com.toptal.quizhub.ports.http.services.api.exceptions.ExceptionConverter;
import com.toptal.quizhub.ports.http.services.api.exceptions.HttpException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import retrofit2.Call;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class TriviaRequester extends HttpRequester {

    private static final String EXCEPTION = "exception";
    private final ExceptionConverter exceptionConverter;

    private final CachedQuestionConverter cachedQuestionConverter;

    public Logger getLogger() {

        return log;
    }

    @Override
    public HttpException getHttpException(int responseCode, String errorBody) throws JsonProcessingException {

        final JsonNode actualObj = JsonUtils.mapToJson(errorBody);
        final JsonNode message = actualObj.get("message");

        final String messageValue;
        messageValue = Objects.nonNull(message) ? message.textValue() : null;

        return exceptionConverter.convert(responseCode, errorBody,
                Objects.nonNull(messageValue) && messageValue.toLowerCase().contains(EXCEPTION) ? null : messageValue);
    }

    public String resetToken(TriviaApi service,
                             String token) {

        final String command = "reset";
        final Call<TriviaTokenOperatorResponse> callable = service.resetToken(command, token);

        TriviaTokenOperatorResponse triviaTokenOperatorResponse=getResponse(callable);

        TriviaResponseHandler.handle(triviaTokenOperatorResponse);

        return triviaTokenOperatorResponse.getToken();
    }

    public String retrieveToken(TriviaApi service) {

        final String command = "request";
        final Call<TriviaTokenOperatorResponse> callable = service.retrieveToken(command);

        TriviaTokenOperatorResponse triviaTokenOperatorResponse=getResponse(callable);

        TriviaResponseHandler.handle(triviaTokenOperatorResponse);

        return triviaTokenOperatorResponse.getToken();
    }

    public List<CachedQuestion> fetchQuestions(TriviaApi service,
                                               QuestionsRefreshCatalog questionsRefreshCatalog) {

        final Call<TriviaQuestionsFetchResponse> callable = service.fetchQuestions(
                questionsRefreshCatalog.getAmount(),
                questionsRefreshCatalog.getCategory(),
                questionsRefreshCatalog.getDifficulty(),
                questionsRefreshCatalog.getType(),
                questionsRefreshCatalog.getToken()
        );

        //we will get 200 ok in most cases but the api has the code in the body to check the actual status
        //for the request
        final TriviaQuestionsFetchResponse triviaQuestionsFetchResponse = getResponse(callable);

        TriviaResponseHandler.handle(questionsRefreshCatalog, triviaQuestionsFetchResponse);

        return cachedQuestionConverter.convert(triviaQuestionsFetchResponse.getResults());

    }
}
