package com.toptal.quizhub.rest.requests.quizzes;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuizCreationRequestDTO {

    @NotBlank
    private String title;

    @NotNull
    private Boolean published;

    @Size(max = 10, message = "List size must be 10")
    private List<QuizQuestionRequestDto> quizQuestionRequestDtoList;

    @Value
    @SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
    public static class QuizQuestionRequestDto {

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
        private String[] incorrectAnswers;
    }

}
