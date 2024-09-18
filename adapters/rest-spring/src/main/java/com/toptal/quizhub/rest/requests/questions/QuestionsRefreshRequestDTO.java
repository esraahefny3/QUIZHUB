package com.toptal.quizhub.rest.requests.questions;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class QuestionsRefreshRequestDTO {

    @NotNull
    @Max(100000)
    Integer totalAmount;

    Integer category;

    String difficulty;

    String type;

}
