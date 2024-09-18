package com.toptal.quizhub.rest.services;

import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.domain.quizzes.QuizUseCase;
import com.toptal.quizhub.rest.converters.quizzes.GetQuizRequestConverter;
import com.toptal.quizhub.rest.converters.quizzes.QuizRequestConverter;
import com.toptal.quizhub.rest.converters.quizzes.QuizResponseConverter;
import com.toptal.quizhub.rest.converters.quizzes.QuizSolutionRequestConverter;
import com.toptal.quizhub.rest.converters.quizzes.QuizUpdateRequestConverter;
import com.toptal.quizhub.rest.requests.quizzes.QuizCreationRequestDTO;
import com.toptal.quizhub.rest.requests.quizzes.QuizSolutionRequestDTO;
import com.toptal.quizhub.rest.requests.quizzes.QuizUpdateRequestDTO;
import com.toptal.quizhub.rest.responses.quizzes.GetQuizResponse;
import com.toptal.quizhub.rest.responses.quizzes.QuizCreationResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuizService {

    private final QuizUseCase quizUseCase;

    private final QuizRequestConverter quizRequestConverter;

    private final QuizUpdateRequestConverter quizUpdateRequestConverter;

    private final QuizResponseConverter quizResponseConverter;

    private final GetQuizRequestConverter getQuizRequestConverter;

    private final QuizSolutionRequestConverter quizSolutionRequestConverter;

    public QuizCreationResponse createQuiz(QuizCreationRequestDTO quizCreationRequestDTO, String authorEmail) {

        return quizResponseConverter.convert(quizUseCase.createQuiz(quizRequestConverter.convert(quizCreationRequestDTO), authorEmail));

    }

    public QuizCreationResponse updateQuiz(UUID quizExternalId, QuizUpdateRequestDTO quizUpdateRequestDTO, String userEmail) {

        return quizResponseConverter.convert(quizUseCase.updateQuiz(quizExternalId, quizUpdateRequestConverter.convert(quizUpdateRequestDTO), userEmail));

    }

    public void deleteQuiz(UUID quizExternalId, String userEmail) {

        quizUseCase.deleteQuiz(quizExternalId, userEmail);

    }

    public GetQuizResponse getQuiz(UUID quizExternalId) {
        return getQuizRequestConverter.convert(quizUseCase.getQuiz(quizExternalId));

    }

    public void publishQuiz(UUID quizExternalId, String userEmail) {


        quizUseCase.publishQuiz(quizExternalId, userEmail);


    }

    public QuizSolutionCatalog solveQuiz(UUID quizExternalId,
                                         QuizSolutionRequestDTO quizSolutionRequestDTO,
                                         String userEmail) {


        return quizUseCase.solveQuiz(quizExternalId,
                quizSolutionRequestConverter.convert(quizSolutionRequestDTO),
                userEmail);

    }
}
