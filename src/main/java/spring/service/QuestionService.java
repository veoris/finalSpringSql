package spring.service;



import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.entity.Question;
import spring.entity.User;
import spring.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    //private GameRepository gameRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

public Question findQuestionById(Long id){
     return questionRepository.findQuestionById(id);
}


    public Optional<Question> loadQuestionByTitle(@NonNull String title) {
        return  Optional.ofNullable(questionRepository.findByTitle(title));
    }

    public List<Question> getAllQuestions() {
        //TODO checking for an empty user list
        return  questionRepository.findAll();
    }

    public void saveNewQuestion (Question question) throws Exception{
        questionRepository.save(question);
    }


//    public Question setGameId(Long gameId, String title){
//
//        Question question = questionRepository.findByTitle(title);
//        question.setGameId(gameId);
//
//        return questionRepository.save(question);
//
//    }
}

