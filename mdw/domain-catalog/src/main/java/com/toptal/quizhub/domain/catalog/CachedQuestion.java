package com.toptal.quizhub.domain.catalog;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class CachedQuestion {

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
