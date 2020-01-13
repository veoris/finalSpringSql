package spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dto.UserDTO;
import spring.service.TeamService;
import spring.service.UserService;

import static java.util.stream.Collectors.joining;
@Slf4j
@Controller
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class MainController {
    private UserService userService;
    private TeamService teamService;

    @Autowired
    public MainController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }


    @RequestMapping("/")
    public String getMainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("teams", teamService.getAllTeams());
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        return "index";
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @GetMapping("/all_users")
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }


    @PostMapping("/choose")
    public String chooseTeam(UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        userService.setTeamId(user.getUsername(),userDTO.getTeamId());
        return "redirect:/";


    }


}
