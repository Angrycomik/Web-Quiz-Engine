package engine.service;


import engine.entity.Quiz;
import engine.exception.UnauthorizedException;
import engine.repository.QuizRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuizService {
    Logger logger = LoggerFactory.getLogger(QuizService.class);

    QuizRepo repository;

    public QuizService(@Autowired QuizRepo repository) {
        this.repository = repository;
    }

    public Quiz getQuiz(Long id){
        Optional<Quiz> quiz = repository.findById(id);
        if(quiz.isPresent()){
            return quiz.get();
        }
        throw new NoSuchElementException();
    }
    public List<Quiz> getAllQuizes(){
        return repository.findAll();
    }

    public Quiz saveQuiz(Quiz quiz){
        logger.info("Saving Quiz: " + quiz.toString());

        quiz.setMadeBy(UserService.getCurrentUsername());
        return repository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        Optional<Quiz> quiz = repository.findById(id);
        if(quiz.isEmpty()){throw new NoSuchElementException();}
        if(quiz.get().getMadeBy().equals(UserService.getCurrentUsername())){
            repository.delete(quiz.get());
            return;
        }
        throw new UnauthorizedException("You are not allowed to delete this Quiz");
    }
}
