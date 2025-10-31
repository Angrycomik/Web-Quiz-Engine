package engine.dto;

import java.util.List;

public record QuizGetDTO(Long id, String title, String text, List<String> options) {
}
