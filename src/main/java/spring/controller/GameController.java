package spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.dto.GameDTO;
import spring.dto.QuestionDTO;
import spring.entity.Game;
import spring.entity.Question;
import spring.service.GameService;
import spring.service.QuestionService;
import spring.service.TeamService;
import spring.service.UserService;

import java.util.Arrays;

@Controller

public class GameController {

    private GameService gameService;
    private QuestionService questionService;
    private TeamService teamService;
    private UserService userService;

    @Autowired
    public GameController(GameService gameService, QuestionService questionService, TeamService teamService, UserService userService) {
        this.gameService = gameService;
        this.questionService = questionService;
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping("/game-config")
    public String game(Model model) {
        model.addAttribute("gameDto", new GameDTO());
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("teams", teamService.getAllTeams());
        return "game-config";
    }


    @GetMapping("/all-games")
    public String getAllGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "all-games" ;
    }

    @PostMapping("/game-config")
    public String addGame(GameDTO gameDTO, QuestionDTO questionDTO) {
        gameService.saveGame(Game.builder()
                .teamId(gameDTO.getTeamId())
                .build());
//        System.out.println("vbcddscsaddsacsdc " + questionDTO.getTitle());
//
//        questionService.setGameId(gameDTO.getTeamId(),questionDTO.getTitle());

        //TODO checkbox get gameId and insert in questions.table (maybe on the next page)
        //TODO return game page
        return "config";
    }

    @GetMapping("/game-questions")
    public String gameQuestions(Model model) {
        //model.addAttribute("gameDto", new GameDTO());
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("teams", teamService.getAllTeams());
        return "game-questions";

    }

    @PostMapping("/game-questions")
    public String addGameQuestions(GameDTO gameDTO) {

        //TODO checkbox get gameId and insert in questions.table (maybe on the next page)

        //TODO return game page
        return "game-questions";
    }


    @GetMapping("/game")
    public String gamePlay(Model model, GameDTO gameDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();


        //TODO

        model.addAttribute("game", gameService.findGameById(gameService.getLastGameId(user.getUsername())));
        // model.addAttribute("questions", gameService.getShuffledQuestions());
        model.addAttribute("questions", gameService.getShuffledQuestions(user.getUsername()));
        return "game";
    }

    @PostMapping("/game")
    public String answer(GameDTO gameDto) {
        //TODO warning hardcode! get game_id
        gameService.setCurrentAnswer(gameDto.getCurrentAnswer(), (long) 59);
        return "redirect:/game";
    }



    @GetMapping("/answer")
    public String answer(Model model, GameDTO gameDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        model.addAttribute("gameDto", gameDTO);
        //TODO

        // model.addAttribute("questions", gameService.getShuffledQuestions());
        model.addAttribute("questions", gameService.getShuffledQuestions(user.getUsername()));
        model.addAttribute("teamAnswer",gameService.findGameById(gameService.getLastGameId(user.getUsername())).getCurrentAnswer());
        return "answer";
    }


    @PostMapping("/answer")
    @RequestMapping(params = "right", method = RequestMethod.POST)
    public String increaseTeamScore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        gameService.increaseTeamScore(gameService.getLastGameId(user.getUsername()));
        return "redirect:/game";
    }

    @PostMapping("/answer")
    @RequestMapping(params = "wrong", method = RequestMethod.POST)
    public String increaseViewerScore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        gameService.increaseViewerScore(gameService.getLastGameId(user.getUsername()));
        return "redirect:/game";
    }


    ///////////////////////////////////////////////

    @GetMapping("/game-answer")
    public String gameAnswer(Model model, QuestionDTO questionDTO) {
        model.addAttribute("gameDto", new GameDTO());
        System.out.println(questionDTO.getTitle());
        model.addAttribute("question", questionService.loadQuestionByTitle(questionDTO.getTitle()));
        return "game-answer";
    }



}
