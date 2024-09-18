package com.toptal.quizhub.http.services.trivia;

import com.toptal.quizhub.http.services.ApiBuilder;
import lombok.Getter;
import retrofit2.Retrofit;

@Getter
public class TriviaServiceContainer {

    private final TriviaApi triviaApi;

    public TriviaServiceContainer(TriviaApiConfiguration triviaApiConfiguration,
                                  Retrofit.Builder retrofitBuilder,
                                  ApiBuilder apiBuilder) {

        this.triviaApi = apiBuilder.buildApi(triviaApiConfiguration.getHost(), retrofitBuilder, TriviaApi.class);
    }

}
