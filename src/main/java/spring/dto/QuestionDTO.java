package spring.dto;

import lombok.Data;

@Data
public class QuestionDTO {

    private Long id;
    private String title;
    private String author;
    private String description;
    private String help;
    private String answer;
    private Long gameId;

}

