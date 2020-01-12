package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring.entity.Game;
import spring.entity.Question;
import spring.entity.QuestionsResult;

import java.util.List;

@Repository
@Component
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long id);

    List<Game> findAll();



   // @Query(value = "SELECT MAX(id) FROM games", nativeQuery = true)
   @Query(value = "select  games.id from games join questions on questions.game_id=games.id join teams on games.team_id=teams.id join users on teams.id=users.team_id and users.username =?1 group by games.id ", nativeQuery = true)
    Long getId(String username);

    //TODO query for value=user.id
    //@Query(value = "select  questions.title from questions join games on questions.game_id=games.id join teams on games.team_id=teams.id join users on teams.id=users.team_id and users.id = 23", nativeQuery = true)
    @Query(value = "select  questions.title from questions join games on questions.game_id=games.id join teams on games.team_id=teams.id join users on teams.id=users.team_id and users.username =?1 ", nativeQuery = true)
    List<String> getGameQuestions(String username);


    @Query(value = "SELECT team_score FROM game.games where games.id=?1 ", nativeQuery = true)
    int getTeamScore(Long id);
    @Query(value = "SELECT viewer_score FROM game.games where games.id=?1 ", nativeQuery = true)
    int getViewerScore(Long id);

}
