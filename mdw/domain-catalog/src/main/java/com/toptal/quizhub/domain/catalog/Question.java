package com.toptal.quizhub.domain.catalog;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class Question {

    private UUID externalId;

    private Quiz quiz;

    private String question;

    private String type;

    private String difficulty;

    private String category;

    private String correctAnswer;

    private String[] incorrectAnswers;

    private Instant createdAt;

    private Instant updatedAt;

}

