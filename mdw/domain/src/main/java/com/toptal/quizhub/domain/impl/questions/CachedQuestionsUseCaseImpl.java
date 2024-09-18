package com.toptal.quizhub.domain.impl.questions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toptal.quizhub.cache.api.CacheFactory;
import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshCatalog;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshRequest;
import com.toptal.quizhub.domain.catalog.exceptions.GetFromCacheError;
import com.toptal.quizhub.domain.catalog.exceptions.SaveInCacheError;
import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.domain.catalog.results.PagedResult;
import com.toptal.quizhub.domain.impl.pagination.PaginationPerformer;
import com.toptal.quizhub.domain.questions.CachedQuestionsUseCase;
import com.toptal.quizhub.ports.http.services.api.question.QuestionApiTokenOperator;
import com.toptal.quizhub.ports.http.services.api.question.QuestionsFetcher;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.toptal.quizhub.ports.http.services.api.question.QuestionsFetcher.*;

@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class CachedQuestionsUseCaseImpl implements CachedQuestionsUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedQuestionsUseCaseImpl.class);

    private final QuestionApiTokenOperator questionApiTokenOperator;

    private final QuestionsFetcher questionFetcher;

    private final CacheFactory<Object> cache;

    private final PaginationPerformer paginationPerformer;

    @Override
    public void refreshQuestions(QuestionsRefreshRequest questionsRefreshRequest) {


        //delete all questions in cache
        cache.deleteAll(REFRESH_QUESTION_PATTERN);

        //get token from cache
        String token = getQuestionApiToken();
        //call trivia api
        //create multiple calls as trivia api dosnt support large amount
        final Integer amount;
        Integer noOfCalls;
        if (questionsRefreshRequest.getTotalAmount() <= REQUEST_MAX_AMOUNT) {
            amount = questionsRefreshRequest.getTotalAmount();
            noOfCalls = 1;
        } else {
            amount = REQUEST_MAX_AMOUNT;
            noOfCalls = questionsRefreshRequest.getTotalAmount() / REQUEST_MAX_AMOUNT;
        }

        while (noOfCalls > 0) {
            final List<CachedQuestion> cachedQuestions = questionFetcher.fetch(
                    QuestionsRefreshCatalog.builder()
                            .amount(amount)
                            .type(questionsRefreshRequest.getType())
                            .difficulty(questionsRefreshRequest.getDifficulty())
                            .category(questionsRefreshRequest.getCategory())
                            .token(token)
                            .build());

            if (cachedQuestions.size() == 0) {
                break;
            }
            //put in cache
            cachedQuestions.stream().forEach(q -> {
                final String key = createCachedQuestionKey(q);
                try {
                    cache.put(key, q);
                } catch (JsonProcessingException e) {
                    LOGGER.error("Error while saving in cache.", e);
                    throw SaveInCacheError.saveInCacheError();
                }
            });

            noOfCalls--;
        }


    }

    @Override
    public PagedResult<CachedQuestion> fetchCachedQuestions(String category, String type, String difficulty, PageRequest pageRequest) {

        final String pattern = String.join(".",
                CACHED_QUESTION_PREFIX,
                Objects.nonNull(category) ? category : "*",
                Objects.nonNull(type) ? type : "*",
                Objects.nonNull(difficulty) ? difficulty : "*",
                "*");


        return paginationPerformer.getPagedResult(pageRequest,
                pagination -> cache.getPaginatedValuesByPattern(
                        pattern,
                        pagination.getOffset(),
                        pageRequest.getLimit(),
                        CachedQuestion.class
                ));


    }


    private String getQuestionApiToken() {

        //Retrieve a Session Token:
        final String token = questionApiTokenOperator.retrieveToken();
        //reset api token
        questionApiTokenOperator.resetToken(token);
        return token;
    }


    private String createCachedQuestionKey(CachedQuestion q) {
        return String.join(".",
                CACHED_QUESTION_PREFIX,
                q.getCategory(),
                q.getType(),
                q.getDifficulty(),
                Integer.toString(ThreadLocalRandom.current().nextInt(REQUEST_MAX_AMOUNT)));
    }
}
