package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring.entity.Question;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByTitle(String login);

    @Query(value = "select  * from questions where questions.id=?1", nativeQuery = true)
    Question findQuestionById(Long id);

    List<Question> findAll();
}
