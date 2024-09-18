package com.toptal.quizhub.rest.responses.quizzes;

import com.toptal.quizhub.domain.catalog.Question;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" }, justification = "Temporarily suppressing. This needs to be checked asap")
public class GetQuizResponse {


    private UUID externalId;

    private String title;

    private List<GetQuizQuestionResponse> questions;

    @Value
    public static class GetQuizQuestionResponse {

        private UUID externalId;
        private String question;

        private String type;

        private String difficulty;

        private String category;

        private String correctAnswer;

        private String[] incorrectAnswers;

    }
}
