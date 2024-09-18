package com.toptal.quizhub.rest.requests.quizzes;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Data
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuizSolutionRequestDTO {

    @Size(max = 10, message = "List size must be 10")
    private List<QuestionSolutionRequestDTO>questionSolutions;

    @Value
    @Valid
    public static class QuestionSolutionRequestDTO {

        @NotBlank
        private UUID externalId;

        @NotBlank
        private String answer;


   }

}
