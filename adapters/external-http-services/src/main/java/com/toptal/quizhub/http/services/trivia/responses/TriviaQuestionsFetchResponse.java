package com.toptal.quizhub.http.services.trivia.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class TriviaQuestionsFetchResponse {

    private Integer responseCode;

    private List<TriviaQuestionResponse> results;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class TriviaQuestionResponse {
        @NotBlank
        private String category;
        @NotBlank
        private String type;
        @NotBlank
        private String difficulty;
        @NotBlank
        private String question;

        @NotBlank
        private String correctAnswer;

        @NotNull
        private List<String> incorrectAnswers;


    }

}
