package engine.converter;

import engine.dto.QuizGetDTO;
import engine.entity.Quiz;

public class converters {
    public static QuizGetDTO convertQuizToQuizGetDTO(Quiz quiz) {
        return new QuizGetDTO(quiz.getId(), quiz.getTitle(), quiz.getText(),quiz.getOptions());
    }
}
