package engine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @NotBlank
    @Schema(description = "Title of the quiz.", example = "Java Basics Quiz")
    String title;

    @NotBlank
    @Schema(description = "The question text for the quiz.", example = "What is the purpose of the JVM?")
    String text;

    @NotNull
    @Size(min = 2)
    @ElementCollection(fetch = FetchType.EAGER)
    @Schema(description = "A list of at least two possible answers.", example = "[\"To compile Java code\", \"To run Java bytecode\", \"To debug code\"]")
    List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "A list of correct answers.", example = "[1]")
    List<Integer> answer;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "The username of the user who created the quiz.", hidden = true)
    private String madeBy;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Internal list of completions.", hidden = true)
    private List<QuizCompletion> completions;

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public Quiz(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Quiz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public List<QuizCompletion> getCompletions() {
        return completions;
    }

    public void setCompletions(List<QuizCompletion> completions) {
        this.completions = completions;
    }
}
