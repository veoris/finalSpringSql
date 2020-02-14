package spring.dto;

import lombok.Data;
import spring.entity.Question;

import java.util.List;

@Data
public class QuestionsDTO {
    private List<Question> question;
}
