package engine.controller;

import engine.adapter.AppUserAdapter;
import engine.dto.AcceptAnswerDTO;
import engine.dto.QuizCompletionDTO;
import engine.dto.AnswerDTO;
import engine.entity.Quiz;
import engine.service.QuizService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    QuizService quizService;

    public QuizController(@Autowired QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);

    }
    @GetMapping()
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") int page){
        return quizService.getAllQuizes(page);
    }

    @GetMapping("/completed")
    public Page<QuizCompletionDTO> getCompletedQuizzes(@RequestParam int page, @AuthenticationPrincipal AppUserAdapter user){
        return quizService.getCompletedQuizzes(page, user.getUser());
    }

    @PostMapping("/{id}/solve")
    public AnswerDTO solveQuiz(@PathVariable Long id, @RequestBody AcceptAnswerDTO dto, @AuthenticationPrincipal AppUserAdapter user) {
        return quizService.solveQuiz(id, dto.answer(), user.getUser());
    }

    @PostMapping
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal AppUserAdapter user) {
        return quizService.saveQuiz(quiz, user.getUsername());
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal AppUserAdapter user) {
        quizService.deleteQuiz(id, user.getUsername());
    }
}
