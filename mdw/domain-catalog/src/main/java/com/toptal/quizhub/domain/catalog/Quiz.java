package com.toptal.quizhub.domain.catalog;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Quiz {


    private UUID externalId;

    private String title;

    private User author;

    private Boolean published;

    private List<Question> questions;
}
