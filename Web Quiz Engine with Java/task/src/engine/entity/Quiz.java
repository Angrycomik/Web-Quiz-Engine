package engine.entity;

import java.util.List;

public record Quiz(String title, String text, List<String> options) {
}
