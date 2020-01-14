package spring.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dto.QuestionDTO;
import spring.entity.Question;
import spring.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findQuestionById(id);
    }

    public Optional<Question> loadQuestionByTitle(@NonNull String title) {
        return Optional.ofNullable(questionRepository.findByTitle(title));
    }

    public List<Question> getAllQuestions() {
        //TODO checking for an empty user list
        return questionRepository.findAll();
    }

    public void saveNewQuestion(QuestionDTO questionDTO)
    {
        questionRepository.save(Question.builder()
                .title(questionDTO.getTitle())
                .description(questionDTO.getDescription())
                .answer(questionDTO.getAnswer())
                .help(questionDTO.getHelp())
                .gameId(questionDTO.getGameId())
                .answered(false)
                .build());
    }
}

