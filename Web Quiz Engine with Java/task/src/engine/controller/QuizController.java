package engine.controller;

import engine.converter.converters;
import engine.dto.QuizGetDTO;
import engine.dto.QuizPostDTO;
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
    public QuizGetDTO getQuiz(@PathVariable Long id) {
        QuizGetDTO quiz = quizService.getQuizDTO(id);
        if(quiz == null){
            throw new NoSuchElementException();
        }
        return quiz;

    }
    @GetMapping
    public List<QuizGetDTO> getQuizzes(){
        return quizService.getAllQuizes();
    }

    @PostMapping("/{id}/solve")
    public Answer acceptAnswer(@PathVariable Long id, @RequestParam int answer) {
        logger.info("accepting answer " + answer + " for quiz id " + id);
        Quiz quiz = quizService.getQuiz(id);

        boolean isCorrect = answer == quiz.getAnswer();

        String feedback = isCorrect ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
        return new Answer(isCorrect, feedback);
    }

    @PostMapping
    public QuizGetDTO receiveQuiz(@RequestBody QuizPostDTO quizPostDTO) {
        Quiz quiz = quizService.saveQuiz(quizPostDTO);
        return converters.convertQuizToQuizGetDTO(quiz);
    }
}
