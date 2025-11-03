package engine.controller;

import engine.dto.AcceptAnswerDTO;
import engine.entity.Answer;
import engine.entity.Quiz;
import engine.service.QuizService;
import engine.util.Utils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    QuizService quizService;

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);

    }
    @GetMapping
    public List<Quiz> getQuizzes(){
        return quizService.getAllQuizes();
    }

    @PostMapping("/{id}/solve")
    public Answer acceptAnswer(@PathVariable Long id, @RequestBody AcceptAnswerDTO dto) {
        logger.info("accepting answer " +  dto.toString() + " for quiz id " + id);
        Quiz quiz = quizService.getQuiz(id);

        boolean isCorrect =  Utils.checkAnswer(quiz.getAnswer(), dto.answer());

        return new Answer(isCorrect);
    }

    @PostMapping
    public Quiz receiveQuiz(@Valid @RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }
}
