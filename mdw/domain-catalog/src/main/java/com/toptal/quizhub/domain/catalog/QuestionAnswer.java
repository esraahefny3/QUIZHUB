package com.toptal.quizhub.domain.catalog;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data

@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuestionAnswer {
    private Long id;

    private UUID externalId;

    private Question question;

    private String selectedAnswer;

    private Integer questionScore;

    private Instant createdAt;

    private Instant updatedAt;


}