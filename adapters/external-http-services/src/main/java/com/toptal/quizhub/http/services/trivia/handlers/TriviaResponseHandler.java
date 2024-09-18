package com.toptal.quizhub.http.services.trivia.handlers;

import com.toptal.quizhub.domain.catalog.QuestionsRefreshCatalog;
import com.toptal.quizhub.http.services.exceptions.InvalidParameterException;
import com.toptal.quizhub.http.services.exceptions.TokenExhaustedException;
import com.toptal.quizhub.http.services.exceptions.TokenNotValidException;
import com.toptal.quizhub.http.services.trivia.responses.TriviaQuestionsFetchResponse;
import com.toptal.quizhub.http.services.trivia.responses.TriviaTokenOperatorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TriviaResponseHandler {

    private static final int INVALID_PARAMETER_CODE = 2;
    private static final int TOKEN_NOT_FOUND_CODE = 3;
    private static final int TOKEN_EMPTY_CODE = 4;

    public static void handle(QuestionsRefreshCatalog questionsRefreshCatalog, TriviaQuestionsFetchResponse triviaQuestionsFetchResponse) {
        switch (triviaQuestionsFetchResponse.getResponseCode()) {
            case INVALID_PARAMETER_CODE: //Invalid Parameter
                throw InvalidParameterException.forParams();
            case TOKEN_NOT_FOUND_CODE: //Token Not valid
                throw TokenNotValidException.forError();

            case TOKEN_EMPTY_CODE: //Token Empty
                throw TokenExhaustedException.forError();

            default: //case 1 (Success) or 2 (No Results)
                //do nothing

        }
    }

    public static void handle(TriviaTokenOperatorResponse triviaTokenOperatorResponse) {
        switch (triviaTokenOperatorResponse.getResponseCode()) {
            case INVALID_PARAMETER_CODE: //Invalid Parameter
                throw InvalidParameterException.forParams();
            case TOKEN_NOT_FOUND_CODE: //Token Not valid
                throw TokenNotValidException.forError();

            case TOKEN_EMPTY_CODE: //Token Empty
                throw TokenExhaustedException.forError();

            default: //case 1 (Success) or 2 (No Results)
                //do nothing

        }
    }
}
