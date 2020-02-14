package spring.dto;

import lombok.Data;
import spring.entity.Team;

import java.util.List;

@Data
public class TeamsDTO {
    private List<Team> teams;
}
