package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring.entity.Question;
import spring.repository.sql.UserSQL;

import java.util.List;

@Repository
@Component
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByTitle(String login);

    @Query(value = UserSQL.FIND_QUESTION, nativeQuery = true)
    Question findQuestionById(Long id);

    List<Question> findAll();
}
