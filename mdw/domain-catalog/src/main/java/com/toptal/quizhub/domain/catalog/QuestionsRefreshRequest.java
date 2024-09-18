package com.toptal.quizhub.domain.catalog;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuestionsRefreshRequest {

    @NotNull
    Integer totalAmount;

    Integer category;

    String difficulty;

    String type;

}
