package engine.dto;

import java.time.LocalDateTime;

public record QuizCompletionDTO(Long id, LocalDateTime completedAt) {}
