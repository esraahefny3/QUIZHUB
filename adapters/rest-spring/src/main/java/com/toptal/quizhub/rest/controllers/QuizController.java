package com.toptal.quizhub.rest.controllers;

import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.rest.constants.RestConstants;
import com.toptal.quizhub.rest.requests.questions.QuestionsRefreshRequestDTO;
import com.toptal.quizhub.rest.requests.quizzes.QuizCreationRequestDTO;
import com.toptal.quizhub.rest.requests.quizzes.QuizSolutionRequestDTO;
import com.toptal.quizhub.rest.requests.quizzes.QuizUpdateRequestDTO;
import com.toptal.quizhub.rest.responses.pagination.PagedResponse;
import com.toptal.quizhub.rest.responses.questions.CachedQuestionResponse;
import com.toptal.quizhub.rest.responses.quizzes.GetQuizResponse;
import com.toptal.quizhub.rest.responses.quizzes.QuizCreationResponse;
import com.toptal.quizhub.rest.services.QuestionService;
import com.toptal.quizhub.rest.services.QuizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(RestConstants.API_V1_ENDPOINT + "/quizzes")
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public QuizCreationResponse createQuiz(@Valid @RequestBody QuizCreationRequestDTO quizCreationRequestDTO, Principal principal) {


        return quizService.createQuiz(quizCreationRequestDTO, principal.getName());

    }

    @PutMapping("{quizExternalId}")
    public QuizCreationResponse updateQuiz(@PathVariable("quizExternalId") UUID quizExternalId,@Valid @RequestBody QuizUpdateRequestDTO quizUpdateRequestDTO, Principal principal) {


         return quizService.updateQuiz(quizExternalId, quizUpdateRequestDTO, principal.getName());

    }


    @DeleteMapping("{quizExternalId}")
    public void deleteQuiz(@PathVariable("quizExternalId") UUID quizExternalId, Principal principal) {


        quizService.deleteQuiz(quizExternalId, principal.getName());

    }
    @GetMapping
    public GetQuizResponse getQuiz(@QueryParam("quizExternalId") String quizExternalId) {


        return quizService.getQuiz(UUID.fromString(quizExternalId));

    }

    @PostMapping("{quizExternalId}/solution")
    public QuizSolutionCatalog solveQuiz(@PathVariable("quizExternalId") UUID quizExternalId,
                                         @Valid @RequestBody QuizSolutionRequestDTO quizSolutionRequestDTO,
                                         Principal principal) {


          return quizService.solveQuiz(quizExternalId,quizSolutionRequestDTO,principal.getName());

    }


}
