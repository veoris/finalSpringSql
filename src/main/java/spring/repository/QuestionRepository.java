package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring.entity.Question;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByTitle(String login);

    List<Question> findAll();
}
