package engine.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

import java.io.Serial;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    String text;

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> options;
    Integer answer;

    public Quiz(String title, String text, List<String> options, Integer answer) {
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

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}
