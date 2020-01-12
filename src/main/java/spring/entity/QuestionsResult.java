package spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
public class QuestionsResult {
    public QuestionsResult(Long id, String title, String description, String help, String answer, Long gameId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.help = help;
        this.answer = answer;
        this.gameId = gameId;
    }

    private Long id;
    private String title;
    private String description;
    private String help;
    private String answer;
    private Long gameId;
}
