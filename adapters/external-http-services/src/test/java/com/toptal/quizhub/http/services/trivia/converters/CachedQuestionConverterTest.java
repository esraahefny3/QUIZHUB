package com.toptal.quizhub.http.services.trivia.converters;

import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.http.services.trivia.converters.questions.CachedQuestionConverter;
import com.toptal.quizhub.http.services.trivia.converters.questions.CachedQuestionConverterImpl;
import com.toptal.quizhub.http.services.trivia.responses.TriviaQuestionsFetchResponse;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CachedQuestionConverterTest {

    private CachedQuestionConverter converter;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        converter = new CachedQuestionConverterImpl();
        faker = new Faker();
    }

    @Test
    public void testConvert_singleQuestion() {
        // Arrange
        final TriviaQuestionsFetchResponse.TriviaQuestionResponse questionResponse = generateRandomQuestionResponse();

        // Act
        final CachedQuestion cachedQuestion = converter.convert(questionResponse);

        // Assert
        assertQuestionResponseEqualsCachedQuestion(questionResponse, cachedQuestion);
    }

    @Test
    public void testConvert_questionList() {
        // Arrange
        final List<TriviaQuestionsFetchResponse.TriviaQuestionResponse> questionResponses = Arrays.asList(
                generateRandomQuestionResponse(),
                generateRandomQuestionResponse(),
                generateRandomQuestionResponse()
        );

        // Act
        final List<CachedQuestion> cachedQuestions = converter.convert(questionResponses);

        // Assert
        Assertions.assertEquals(questionResponses.size(), cachedQuestions.size());

        for (int i = 0; i < questionResponses.size(); i++) {
            final TriviaQuestionsFetchResponse.TriviaQuestionResponse questionResponse =
                    questionResponses.get(i);
            final CachedQuestion cachedQuestion = cachedQuestions.get(i);
            assertQuestionResponseEqualsCachedQuestion(questionResponse, cachedQuestion);
        }
    }

    private TriviaQuestionsFetchResponse.TriviaQuestionResponse generateRandomQuestionResponse() {
        final String category = faker.lorem().word();
        final String type = faker.lorem().word();
        final String difficulty = faker.lorem().word();
        final String question = faker.lorem().sentence();
        final String correctAnswer = faker.lorem().word();
        final List<String> incorrectAnswers = Arrays.asList(faker.lorem().word(),
                faker.lorem().word(),
                faker.lorem().word());

        final TriviaQuestionsFetchResponse.TriviaQuestionResponse questionResponse =
                new TriviaQuestionsFetchResponse.TriviaQuestionResponse();
        questionResponse.setCategory(category);
        questionResponse.setType(type);
        questionResponse.setDifficulty(difficulty);
        questionResponse.setQuestion(question);
        questionResponse.setCorrectAnswer(correctAnswer);
        questionResponse.setIncorrectAnswers(incorrectAnswers);
        return questionResponse;
    }

    private void assertQuestionResponseEqualsCachedQuestion(
            TriviaQuestionsFetchResponse.TriviaQuestionResponse questionResponse,
            CachedQuestion cachedQuestion) {

        Assertions.assertEquals(questionResponse.getCategory(), cachedQuestion.getCategory());
        Assertions.assertEquals(questionResponse.getType(), cachedQuestion.getType());
        Assertions.assertEquals(questionResponse.getDifficulty(), cachedQuestion.getDifficulty());
        Assertions.assertEquals(questionResponse.getQuestion(), cachedQuestion.getQuestion());
        Assertions.assertEquals(questionResponse.getCorrectAnswer(), cachedQuestion.getCorrectAnswer());
        Assertions.assertEquals(questionResponse.getIncorrectAnswers(), cachedQuestion.getIncorrectAnswers());
    }
}
