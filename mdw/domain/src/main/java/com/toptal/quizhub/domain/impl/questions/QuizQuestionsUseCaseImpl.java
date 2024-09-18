package com.toptal.quizhub.domain.impl.questions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toptal.quizhub.cache.api.CacheFactory;
import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshCatalog;
import com.toptal.quizhub.domain.catalog.QuestionsRefreshRequest;
import com.toptal.quizhub.domain.catalog.exceptions.GetFromCacheError;
import com.toptal.quizhub.domain.catalog.exceptions.SaveInCacheError;
import com.toptal.quizhub.domain.questions.QuizQuestionsUseCase;
import com.toptal.quizhub.domain.questions.CachedQuestionsUseCase;
import com.toptal.quizhub.ports.http.services.api.question.QuestionApiTokenOperator;
import com.toptal.quizhub.ports.http.services.api.question.QuestionsFetcher;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.toptal.quizhub.ports.http.services.api.question.QuestionsFetcher.*;

@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuizQuestionsUseCaseImpl implements QuizQuestionsUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizQuestionsUseCaseImpl.class);


}
