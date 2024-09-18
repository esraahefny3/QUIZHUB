package com.toptal.quizhub.rest.services;

import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.domain.catalog.results.PagedResult;
import com.toptal.quizhub.domain.questions.QuizQuestionsUseCase;
import com.toptal.quizhub.domain.questions.CachedQuestionsUseCase;
import com.toptal.quizhub.rest.converters.paginations.PagedResponseConverter;
import com.toptal.quizhub.rest.converters.questions.CachedQuestionResponseConverter;
import com.toptal.quizhub.rest.converters.questions.QuestionsRefreshRequestConverter;
import com.toptal.quizhub.rest.requests.questions.QuestionsRefreshRequestDTO;
import com.toptal.quizhub.rest.responses.pagination.PagedResponse;
import com.toptal.quizhub.rest.responses.questions.CachedQuestionResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuestionService {

    private final CachedQuestionsUseCase cachedQuestionsUseCase;

    private final QuizQuestionsUseCase refreshQuestionsUseCase;

    private final PagedResponseConverter pagedResponseConverter;

    private final CachedQuestionResponseConverter cachedQuestionResponseConverter;

    private final QuestionsRefreshRequestConverter questionsRefreshRequestConverter;

    public void refreshQuestions(QuestionsRefreshRequestDTO questionsRefreshRequestDTO) {


        cachedQuestionsUseCase.refreshQuestions(questionsRefreshRequestConverter.convert(questionsRefreshRequestDTO));


    }

    public PagedResponse<CachedQuestionResponse> fetchCachedQuestions(Integer limit, Integer offset, String category, String type,String difficulty) {

        final PageRequest pageRequest = PageRequest.of(offset, limit);
        PagedResult<CachedQuestion> cachedQuestionPagedResult= cachedQuestionsUseCase.fetchCachedQuestions(category,type,difficulty,pageRequest);

        return pagedResponseConverter.convert(cachedQuestionPagedResult, cachedQuestionResponseConverter::convert);
    }

}
