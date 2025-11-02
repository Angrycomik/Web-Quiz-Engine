package engine.controller;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    public Answer acceptAnswer(@PathVariable Long id, @RequestParam int answer) {
        logger.info("accepting answer " + answer + " for quiz id " + id);
        Quiz quiz = quizService.getQuiz(id);

        boolean isCorrect = answer == quiz.getAnswer();

        return new Answer(isCorrect);
    }

    @PostMapping
    public Quiz receiveQuiz(@RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }
}
