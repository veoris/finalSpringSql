package spring.dto;

import lombok.Data;

@Data
public class GameDTO {
    private int viewerScore;
    private int teamScore;
    private Long teamId;
    private Long gameId;
    private String currentAnswer;
    private Long currentQuestionId;
    private Long id;
}
