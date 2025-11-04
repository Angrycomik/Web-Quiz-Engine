package engine.service;


import engine.dto.QuizCompletionDTO;
import engine.entity.Answer;
import engine.entity.AppUser;
import engine.entity.Quiz;
import engine.entity.QuizCompletion;
import engine.exception.UnauthorizedException;
import engine.repository.QuizCompletionRepository;
import engine.repository.QuizRepository;
import engine.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuizService {
    Logger logger = LoggerFactory.getLogger(QuizService.class);

    QuizRepository quizRepository;
    QuizCompletionRepository completionRepo;

    public QuizService(@Autowired QuizRepository quizRepository, @Autowired QuizCompletionRepository completionRepo) {
        this.quizRepository = quizRepository;
        this.completionRepo = completionRepo;
    }

    public Quiz getQuiz(Long id){
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isPresent()){
            return quiz.get();
        }
        throw new NoSuchElementException();
    }

    public Page<Quiz> getAllQuizes(int page){
        PageRequest pageRequest = PageRequest.of(page, 10);
        return quizRepository.findAll(pageRequest);
    }

    public Page<QuizCompletionDTO> getCompletedQuizzes(int page, AppUser user) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return completionRepo.findAllByUser(user, pageRequest).map(i -> new QuizCompletionDTO(i.getQuiz().getId(),  i.getCompletedAt()));
    }

    public Answer solveQuiz(Long quizId, List<Integer> correctAnswers, AppUser user) {
        Quiz quiz = getQuiz(quizId);
        boolean isCorrect = Utils.checkAnswer(quiz.getAnswer(), correctAnswers);

        if (isCorrect) {
            QuizCompletion completion = new QuizCompletion(quiz, user, LocalDateTime.now());
            completionRepo.save(completion);
        }
        return new Answer(isCorrect);
    }

    public Quiz saveQuiz(Quiz quiz){
        logger.info("Saving Quiz: " + quiz.toString());

        quiz.setMadeBy(UserService.getCurrentUsername());
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isEmpty()){throw new NoSuchElementException();}
        if(quiz.get().getMadeBy().equals(UserService.getCurrentUsername())){
            quizRepository.delete(quiz.get());
            return;
        }
        throw new UnauthorizedException("You are not allowed to delete this Quiz");
    }
}
