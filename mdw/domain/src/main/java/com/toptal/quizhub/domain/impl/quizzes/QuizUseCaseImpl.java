package com.toptal.quizhub.domain.impl.quizzes;

import com.toptal.quizhub.domain.catalog.Quiz;
import com.toptal.quizhub.domain.catalog.QuizSolution;
import com.toptal.quizhub.domain.catalog.QuizSolutionCatalog;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.domain.catalog.exceptions.*;
import com.toptal.quizhub.domain.quizzes.QuizUseCase;
import com.toptal.quizhub.persistence.api.daos.QuizDAO;
import com.toptal.quizhub.persistence.api.daos.QuizSolutionDAO;
import com.toptal.quizhub.persistence.api.daos.UserDAO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.ws.rs.NotAllowedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class QuizUseCaseImpl implements QuizUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizUseCaseImpl.class);
    private final QuizDAO quizDAO;
    private final UserDAO userDAO;
    private final QuizSolutionDAO quizSolutionDAO;

    @Override
    public Quiz createQuiz(Quiz quiz, String authorEmail) {

        Optional<User> user = userDAO.findByEmail(authorEmail);
        if (user.isPresent()) {
            quiz.setAuthor(user.get());
            return quizDAO.insert(quiz);
        } else {
            throw MissingMandatoryFieldException.forField("userInfo");
        }
    }

    @Override
    public Quiz updateQuiz(UUID quizExternalId, Quiz updatedQuiz, String userEmail) {
        Optional<User> user = userDAO.findByEmail(userEmail);
        if (user.isPresent()) {
            User currentUser = user.get();
            Optional<Quiz> quizOptional = quizDAO.findByExternalId(quizExternalId);
            if (quizOptional.isPresent()) {
                Quiz existingQuiz = quizOptional.get();
                if (existingQuiz.getPublished() == true) {
                    LOGGER.error(String.format("Quiz with externalId [%s] is already in status published. Cannot Update", quizExternalId));
                    throw QuizAlreadyPublishedException.forExternalId(quizExternalId);
                }
                if (existingQuiz.getAuthor().getSid().equals(currentUser.getSid())) {
                    //update
                    updatedQuiz.setExternalId(quizExternalId);
                    return quizDAO.updateQuiz(updatedQuiz);

                } else {
                    LOGGER.error(String.format("User cannot update this resource"));
                    throw new ForbiddenException();
                }

            } else {
                LOGGER.error(String.format("Quiz with externalId [%s] is not found", quizExternalId));
                throw QuizNotFoundException.forExternalId(quizExternalId);
            }

        } else {
            LOGGER.error(String.format(String.format("[%s] is mandatory but not found", "user")));
            throw MissingMandatoryFieldException.forField("user");
        }
    }


    public void publishQuiz(UUID quizExternalId, String userEmail) {

        Optional<User> user = userDAO.findByEmail(userEmail);
        if (user.isPresent()) {
            User currentUser = user.get();
            Optional<Quiz> quizOptional = quizDAO.findByExternalId(quizExternalId);
            if (quizOptional.isPresent()) {
                Quiz existingQuiz = quizOptional.get();
                if (existingQuiz.getPublished() == true) {
                    LOGGER.error(String.format("Quiz with externalId [%s] is already in status published", quizExternalId));
                    throw QuizAlreadyPublishedException.forExternalId(quizExternalId);
                }
                if (existingQuiz.getAuthor().getSid().equals(currentUser.getSid())) {
                    Quiz updatedQuiz = new Quiz();
                    updatedQuiz.setExternalId(quizExternalId);
                    updatedQuiz.setPublished(true);
                    quizDAO.updateQuiz(updatedQuiz);
                } else {
                    LOGGER.error(String.format("User cannot update this resource"));
                    throw new ForbiddenException();
                }

            } else {
                LOGGER.error(String.format("Quiz with externalId [%s] is not found", quizExternalId));
                throw QuizNotFoundException.forExternalId(quizExternalId);
            }

        } else {
            LOGGER.error(String.format(String.format("[%s] is mandatory but not found", "user")));
            throw MissingMandatoryFieldException.forField("user");
        }
    }

    @Override
    public void deleteQuiz(UUID quizExternalId, String userEmail) {

        Optional<User> user = userDAO.findByEmail(userEmail);
        if (user.isPresent()) {
            User currentUser = user.get();
            Optional<Quiz> quizOptional = quizDAO.findByExternalId(quizExternalId);
            if (quizOptional.isPresent()) {
                Quiz existingQuiz = quizOptional.get();
                if (existingQuiz.getPublished() == true) {
                    LOGGER.error(String.format("Quiz with externalId [%s] is already in status published", quizExternalId));
                    throw QuizAlreadyPublishedException.forExternalId(quizExternalId);
                }
                if (existingQuiz.getAuthor().getSid().equals(currentUser.getSid())) {

                    quizDAO.deleteQuiz(quizExternalId);
                } else {
                    LOGGER.error(String.format("User cannot update this resource"));
                    throw new ForbiddenException();
                }

            } else {
                LOGGER.error(String.format("Quiz with externalId [%s] is not found", quizExternalId));
                throw QuizNotFoundException.forExternalId(quizExternalId);
            }

        } else {
            LOGGER.error(String.format(String.format("[%s] is mandatory but not found", "user")));
            throw MissingMandatoryFieldException.forField("user");
        }

    }

    @Override
    public Quiz getQuiz(UUID quizExternalId) {

        Optional<Quiz>quizOptional= quizDAO.findByExternalId(quizExternalId);
        if(quizOptional.isPresent())
        {
            return quizOptional.get();
        }
        else {
            throw QuizNotFoundException.forExternalId(quizExternalId);
        }
    }

    @Override
    public QuizSolutionCatalog solveQuiz(UUID quizExternalId, QuizSolution quizSolution, String userEmail) {

        //check if answered before
        User user=userDAO.findByEmail(userEmail).orElseThrow(()->  MissingMandatoryFieldException.forField("user"));
        Quiz quiz=quizDAO.findByExternalId(quizExternalId).orElseThrow(()->QuizNotFoundException.forExternalId(quizExternalId));
        if(quiz.getPublished()==false)
        {
            throw new RuntimeException("Cannot submit solution for unpublished quiz");
        }
        Boolean present=quizSolutionDAO.findBy(quiz,user).isPresent();
        if(present)
        {
            throw new RuntimeException("Cannot submit new answer of already taken quiz");
        }


        //else we will presist the answer
        return quizSolutionDAO.save(quizSolution,quiz,userEmail)
                                                    .orElseThrow(()->new RuntimeException("Error while comuting quiz result"));


    }
}
