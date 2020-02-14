package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.dto.TeamDTO;
import spring.entity.Team;
import spring.service.TeamService;
import spring.service.UserService;

@Controller
public class TeamController {

    private TeamService teamService;
    private UserService userService;

    @Autowired
    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping("/team")
    public String team(Model model) {
        model.addAttribute("teamDto", new TeamDTO());
        model.addAttribute("users", userService.getAllUsers());
        return "team";
    }

    @GetMapping("/all-teams")
    public String getAllTeams(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        return "teams";
    }

    @PostMapping("/team")
    public String addTeam(TeamDTO teamDTO, Model model) {
        try {
            teamService.saveTeam(Team.builder()
                    .name(teamDTO.getName())
                    .build());
        } catch (Exception ex) {
            model.addAttribute("message", "Team is already exist");
            return "team";
        }
        return "team";
    }
}

