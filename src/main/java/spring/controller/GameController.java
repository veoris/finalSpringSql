package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.dto.GameDTO;
import spring.service.GameService;

@Controller
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game")
    public String gamePlay(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        model.addAttribute("game", gameService.findGameById(gameService.getLastGameId(user.getUsername())));
        model.addAttribute("questions", gameService.getShuffledQuestions(user.getUsername(), gameService.getLastGameId(user.getUsername())));
        return "game";
    }

    @PostMapping("/game")
    public String answer(GameDTO gameDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        gameService.currentAnswer(user, gameDto);
        return "redirect:/game";
    }

}
