package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring.entity.Game;
import spring.repository.sql.UserSQL;

import java.util.List;

@Repository
@Component
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long id);

    List<Game> findAll();

    @Query(value = UserSQL.GET_GAME_CURRENT_QUESTION_ID, nativeQuery = true)
    Long getCurrentQuestionId(Long gameId);

    @Query(value = UserSQL.GET_LAST_GAME_ID, nativeQuery = true)
    Long getId(String username);

    @Query(value = UserSQL.GET_GAME_QUESTIONS, nativeQuery = true)
    List<String> getGameQuestions(String username, Long gameId);


    @Query(value = UserSQL.GET_TEAM_SCORE, nativeQuery = true)
    int getTeamScore(Long id);

    @Query(value = UserSQL.GET_VIEWER_SCORE, nativeQuery = true)
    int getViewerScore(Long id);

}
