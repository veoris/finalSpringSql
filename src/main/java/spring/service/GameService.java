package spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import spring.dto.GameDTO;
import spring.entity.Game;
import spring.entity.Question;
import spring.repository.GameRepository;
import spring.repository.QuestionRepository;
import spring.repository.TeamRepository;
import spring.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private GameRepository gameRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public GameService(GameRepository gameRepository, QuestionRepository questionRepository) {
        this.gameRepository = gameRepository;
        this.questionRepository = questionRepository;
    }

public Game findGameById(Long id){
        return gameRepository.findGameById(id);

}

    public List<Game> getAllGames() {
        //TODO checking for an empty user list

        return gameRepository.findAll();
    }

    public void saveGame(GameDTO gameDTO) {
        gameRepository.save(Game.builder()
                .teamId(gameDTO.getTeamId())
                .build());

    }

    public Long getLastGameId(String username) {
        return gameRepository.getId(username);
    }

    public List<String> getGameQuestions(String username, Long gameId){
        return gameRepository.getGameQuestions(username, gameId);
    }

    public Question getShuffledQuestions(String username,Long gameId){
        List<Question> questions = new ArrayList<>();
        for (String title:getGameQuestions(username,gameId)) {
            questions.add(questionRepository.findByTitle(title));

        }
        //TODO shuffle
        Collections.shuffle(questions, new Random());
        return questions.get(gameRepository.getTeamScore(getLastGameId(username))+gameRepository.getViewerScore(getLastGameId(username)));
    }

    public Long getCurrentQuestionId(Long gameId){
        return gameRepository.getCurrentQuestionId(gameId);
    }

    public void setCurrentAnswer(String answer, Long gameId, Long questionId){
        Game game = gameRepository.findGameById(gameId);
        game.setCurrentAnswer(answer);
        game.setCurrentQuestionId(questionId);
        gameRepository.save(game);
    }

    public void currentAnswer(UserDetails user, GameDTO gameDto){
        setCurrentAnswer(gameDto.getCurrentAnswer(), getLastGameId(user.getUsername()), getShuffledQuestions(user.getUsername(),getLastGameId(user.getUsername())).getId());
    }


    public void increaseTeamScore(Long gameId){
        Game game = gameRepository.findGameById(gameId);
        game.setTeamScore(game.getTeamScore()+1);
        gameRepository.save(game);
    }

    public void increaseViewerScore(Long gameId){
        Game game = gameRepository.findGameById(gameId);
        game.setViewerScore(game.getViewerScore()+1);
        gameRepository.save(game);
    }

}

