package engine.dto;

import java.util.List;

public record QuizPostDTO(String title, String text, List<String> options, Integer answer ){
}
