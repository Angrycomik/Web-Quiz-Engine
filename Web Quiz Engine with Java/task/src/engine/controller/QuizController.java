package engine.controller;

import engine.entity.Answer;
import engine.entity.Quiz;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @GetMapping
    public Quiz getQuiz(){
        return new Quiz("The Java Logo","What is depicted on the Java logo?", List.of("Robot","Tea leaf","Cup of coffee","Bug"));
    }
    @PostMapping
    public Answer acceptAnswer(@RequestParam Integer answer){
        boolean isCorrect = answer == 2;
        String feedback = isCorrect ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
        return new Answer(isCorrect, feedback);
    }
}
