package spring.repository.sql;

public interface UserSQL {
    String FIND_QUESTION = "select  * from questions where questions.id=?1";
    String GET_GAME_CURRENT_QUESTION_ID = "SELECT current_question_id FROM game.games where games.id=?1";
    String GET_LAST_GAME_ID = " select max(games.id) from games join questions on questions.game_id=games.id join teams on games.team_id=teams.id join users on teams.id=users.team_id and users.username =?1 ";
    String GET_GAME_QUESTIONS = "select  questions.title from questions join games on questions.game_id=games.id join teams on games.team_id=teams.id join users on teams.id=users.team_id and users.username =?1 and game_id=?2";
    String GET_TEAM_SCORE = "SELECT team_score FROM game.games where games.id=?1 ";
    String GET_VIEWER_SCORE = "SELECT viewer_score FROM game.games where games.id=?1 ";
}
