package engine.controller;

import engine.adapter.AppUserAdapter;
import engine.dto.AcceptAnswerDTO;
import engine.dto.QuizCompletionDTO;
import engine.dto.AnswerDTO;
import engine.entity.Quiz;
import engine.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@Tag(name = "2. Quiz Management", description = "Endpoints for creating, solving, and managing quizzes.")
@SecurityRequirement(name = "basicAuth")
public class QuizController {
    QuizService quizService;

    public QuizController(@Autowired QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "4. Get a single quiz by ID",
            description = "Retrieves a quiz by its ID. The correct answer is not included.")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);

    }
    @GetMapping()
    @Operation(summary = "2. Get a paginated list of all quizzes",
            description = "Provides a list of all quizzes, 10 per page. Use the 'page' param to navigate.")
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") int page){
        return quizService.getAllQuizes(page);
    }

    @GetMapping("/completed")
    @Operation(summary = "5. Get completed quizzes for the user",
            description = "Returns a paginated list of all quizzes solved correctly by the authenticated user.")
    public Page<QuizCompletionDTO> getCompletedQuizzes(@RequestParam int page, @AuthenticationPrincipal AppUserAdapter user){
        return quizService.getCompletedQuizzes(page, user.getUser());
    }

    @PostMapping("/{id}/solve")
    @Operation(summary = "3. Solve a quiz",
            description = "Submits an answer to a specific quiz. Returns a success/failure response. Records completion on success.")
    public AnswerDTO solveQuiz(@PathVariable Long id, @RequestBody AcceptAnswerDTO dto, @AuthenticationPrincipal AppUserAdapter user) {
        return quizService.solveQuiz(id, dto.answer(), user.getUser());
    }

    @PostMapping
    @Operation(summary = "1. Create a new quiz",
            description = "Creates a new quiz. The authenticated user will be set as the author.")
    @Order(1)
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal AppUserAdapter user) {
        return quizService.saveQuiz(quiz, user.getUsername());
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "6. Delete a quiz",
            description = "Deletes a quiz. The authenticated user must be the author of the quiz.")
    public void deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal AppUserAdapter user) {
        quizService.deleteQuiz(id, user.getUsername());
    }
}
