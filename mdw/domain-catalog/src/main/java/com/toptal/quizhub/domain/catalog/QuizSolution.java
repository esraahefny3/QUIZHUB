package com.toptal.quizhub.domain.catalog;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Data
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuizSolution {

    List<QuestionSolution>questionSolutions;

    @Value
    public static class QuestionSolution {

        @NotBlank
        private UUID externalId;

        @NotBlank
        private String answer;


   }

}
