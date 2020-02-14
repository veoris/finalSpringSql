package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring.entity.Team;

import java.util.List;

@Repository
@Component
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);

    List<Team> findAll();
}
