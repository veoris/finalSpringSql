package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring.entity.User;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);
}
