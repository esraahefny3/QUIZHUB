package com.toptal.quizhub.app.configurations.http.services.trivia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.quizhub.app.configurations.http.services.RetrofitBuilderFactory;
import com.toptal.quizhub.http.services.ApiBuilder;
import com.toptal.quizhub.http.services.trivia.TriviaServiceContainer;
import com.toptal.quizhub.http.services.trivia.converters.questions.CachedQuestionConverter;
import com.toptal.quizhub.http.services.trivia.requesters.TriviaQuestionApiTokenOperator;
import com.toptal.quizhub.http.services.trivia.requesters.TriviaQuestionsFetcher;
import com.toptal.quizhub.http.services.trivia.requesters.TriviaRequester;
import com.toptal.quizhub.ports.http.services.api.exceptions.ExceptionConverter;
import com.toptal.quizhub.ports.http.services.api.question.QuestionApiTokenOperator;
import com.toptal.quizhub.ports.http.services.api.question.QuestionsFetcher;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

@Configuration
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class TriviaConfiguration {

    private final TriviaApiConfigurationImpl configuration;

    private final RetrofitBuilderFactory retrofitBuilderFactory;

    @Bean
    TriviaRequester triviaRequester(ExceptionConverter exceptionConverter, CachedQuestionConverter cachedQuestionConverter) {

        return new TriviaRequester(exceptionConverter, cachedQuestionConverter);
    }


    @Bean
    TriviaServiceContainer triviaServiceContainer(ObjectMapper objectMapper, ApiBuilder apiBuilder) {

        final Retrofit.Builder retrofitBuilder = retrofitBuilderFactory.make(objectMapper, configuration);
        return new TriviaServiceContainer(configuration, retrofitBuilder, apiBuilder);
    }

    @Bean
    QuestionApiTokenOperator tokenOperator(TriviaServiceContainer triviaServiceContainer, TriviaRequester triviaRequester) {

        return new TriviaQuestionApiTokenOperator(triviaServiceContainer, triviaRequester);
    }

    @Bean
    QuestionsFetcher questionsFetcher(TriviaServiceContainer triviaServiceContainer, TriviaRequester triviaRequester) {

        return new TriviaQuestionsFetcher(triviaServiceContainer, triviaRequester);
    }

    @Bean
    CachedQuestionConverter cachedQuestionConverter() {
        return Mappers.getMapper(CachedQuestionConverter.class);
    }
}
