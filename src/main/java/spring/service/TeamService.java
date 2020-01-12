package spring.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.entity.Team;
import spring.entity.User;
import spring.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private TeamRepository teamRepository;


    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;

    }


    public Optional<Team> loadTeamByName(@NonNull String name) throws UsernameNotFoundException {
        return Optional.ofNullable(teamRepository.findByName(name));
    }

    public List<Team> getAllTeams() {
        //TODO checking for an empty user list

        return teamRepository.findAll();
    }

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    public Long getId(Team team){
        return team.getId();
    }
}
