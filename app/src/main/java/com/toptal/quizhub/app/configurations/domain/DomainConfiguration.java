package com.toptal.quizhub.app.configurations.domain;

import com.toptal.quizhub.app.accessors.ApplicationContextAccessorImpl;
import com.toptal.quizhub.auth.PasswordEncoderHelper;
import com.toptal.quizhub.cache.api.CacheFactory;
import com.toptal.quizhub.commons.utils.ApplicationContextAccessor;
import com.toptal.quizhub.domain.catalog.CachedQuestion;
import com.toptal.quizhub.domain.impl.pagination.PaginationPerformer;
import com.toptal.quizhub.domain.impl.questions.CachedQuestionsUseCaseImpl;
import com.toptal.quizhub.domain.impl.questions.QuizQuestionsUseCaseImpl;
import com.toptal.quizhub.domain.impl.quizzes.QuizUseCaseImpl;
import com.toptal.quizhub.domain.impl.users.AuthUserUseCaseImpl;
import com.toptal.quizhub.domain.impl.users.GetUserDetailsUseCaseImpl;
import com.toptal.quizhub.domain.impl.users.SaveUserDetailsUseCaseImpl;
import com.toptal.quizhub.domain.questions.CachedQuestionsUseCase;
import com.toptal.quizhub.domain.questions.QuizQuestionsUseCase;
import com.toptal.quizhub.domain.quizzes.QuizUseCase;
import com.toptal.quizhub.domain.users.AuthUserUseCase;
import com.toptal.quizhub.domain.users.GetUserDetailsUseCase;
import com.toptal.quizhub.domain.users.SaveUserDetailsUseCase;
import com.toptal.quizhub.persistence.api.daos.QuizDAO;
import com.toptal.quizhub.persistence.api.daos.QuizSolutionDAO;
import com.toptal.quizhub.persistence.api.daos.RoleDAO;
import com.toptal.quizhub.persistence.api.daos.UserDAO;
import com.toptal.quizhub.ports.http.services.api.question.QuestionApiTokenOperator;
import com.toptal.quizhub.ports.http.services.api.question.QuestionsFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    ApplicationContextAccessor applicationContextAccessor() {

        return new ApplicationContextAccessorImpl();
    }


    @Bean
    CachedQuestionsUseCase refreshQuestions(QuestionApiTokenOperator tokenOperator,
                                            QuestionsFetcher questionsFetcher, CacheFactory<Object> cache,
                                            PaginationPerformer paginationPerformer) {

        return new CachedQuestionsUseCaseImpl(tokenOperator, questionsFetcher, cache, paginationPerformer);
    }


    @Bean
    QuizQuestionsUseCase quizQuestionsUseCase() {

        return new QuizQuestionsUseCaseImpl();
    }


    @Bean
    QuizUseCase quizUseCase(QuizDAO quizDAO, UserDAO userDAO, QuizSolutionDAO quizSolutionDAO) {

        return new QuizUseCaseImpl(quizDAO, userDAO,quizSolutionDAO);
    }


    @Bean
    GetUserDetailsUseCase getUserDetails(UserDAO userDAO) {

        return new GetUserDetailsUseCaseImpl(userDAO);
    }

    @Bean
    SaveUserDetailsUseCase saveUserDetailsUseCase(UserDAO userDAO) {

        return new SaveUserDetailsUseCaseImpl(userDAO);
    }

    @Bean
    AuthUserUseCase registerUserUseCase(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoderHelper passwordEncoderHelper) {

        return new AuthUserUseCaseImpl(userDAO, roleDAO, passwordEncoderHelper);
    }


}
