package com.toptal.quizhub.http.services.trivia;

import com.toptal.quizhub.http.services.trivia.responses.TriviaQuestionsFetchResponse;
import com.toptal.quizhub.http.services.trivia.responses.TriviaTokenOperatorResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TriviaApi {
    @GET("/api_token.php")
    Call<TriviaTokenOperatorResponse> resetToken(@Query("command") String command, @Query("token") String token);

    @GET("/api_token.php")
    Call<TriviaTokenOperatorResponse> retrieveToken(@Query("command") String command);

    @GET("/api.php")
    Call<TriviaQuestionsFetchResponse> fetchQuestions(@Query("amount") Integer amount,
                                                      @Query("category") Integer category,
                                                      @Query("difficulty") String difficulty,
                                                      @Query("type") String type,
                                                      @Query("token") String token);

}

