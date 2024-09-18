package com.toptal.quizhub.rest.controllers;

import com.toptal.quizhub.rest.constants.RestConstants;
import com.toptal.quizhub.rest.requests.questions.QuestionsRefreshRequestDTO;
import com.toptal.quizhub.rest.responses.pagination.PagedResponse;
import com.toptal.quizhub.rest.responses.questions.CachedQuestionResponse;
import com.toptal.quizhub.rest.services.QuestionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(RestConstants.API_V1_ENDPOINT + "/questions")
public class QuestionController {
    public static final int LIST_MAX_LIMIT = 50;

    private final QuestionService questionService;

    @PostMapping("/refresh")
    public void refreshQuestions(@Valid @RequestBody QuestionsRefreshRequestDTO questionsRefreshRequestDTO) {

        questionService.refreshQuestions(questionsRefreshRequestDTO);
    }


    @GetMapping
    public PagedResponse<CachedQuestionResponse> fetchCachedQuestions(@RequestParam(defaultValue = "25") @Min(1) @Max(LIST_MAX_LIMIT) Integer limit,
                                                                      @RequestParam(defaultValue = "1") @Min(1) Integer offset,
                                                                      @QueryParam("category") String category,
                                                                      @QueryParam("type") String type,
                                                                      @QueryParam("difficulty") String difficulty) {

        return questionService.fetchCachedQuestions(limit, offset, category, type, difficulty);
    }


}
