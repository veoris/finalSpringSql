package spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dto.GameDTO;
import spring.dto.QuestionDTO;
import spring.entity.Game;
import spring.service.GameService;
import spring.service.QuestionService;
import spring.service.TeamService;
import spring.service.UserService;

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



    @PostMapping("/game-config")
    public String addGame(GameDTO gameDTO) {
        gameService.saveGame(Game.builder()
                .teamId(gameDTO.getTeamId())
                .build());
        //TODO checkbox get gameId and insert in questions.table (maybe on the next page)
        //TODO return game page
        return "redirect:/config";
    }

    @GetMapping("/game-questions")
    public String gameQuestions(Model model) {
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
        model.addAttribute("questions", gameService.getShuffledQuestions(user.getUsername(),gameService.getLastGameId(user.getUsername())));
        return "game";
    }

    @PostMapping("/game")
    public String answer(GameDTO gameDto) {
        //TODO warning hardcode! get game_id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        gameService.setCurrentAnswer(gameDto.getCurrentAnswer(), gameService.getLastGameId(user.getUsername()), gameService.getShuffledQuestions(user.getUsername(),gameService.getLastGameId(user.getUsername())).getId());
        return "redirect:/game";
    }


    @RequestMapping(value = "/all-games", method = RequestMethod.GET)
    public String getAllGames(Model model) {
        GameDTO gameDTO = new GameDTO();
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("gameDto", gameDTO);
        return "all-games";
    }


    @RequestMapping(value="/all-games", method=RequestMethod.POST)
    public String chooseGame(@ModelAttribute GameDTO gameDTO, Model model) {
        Long id = gameService.findGameById((long) gameDTO.getTeamScore()).getId();
        Long id1 =questionService.findQuestionById(gameService.getCurrentQuestionId((long) gameDTO.getTeamScore())).getId()-1;
        model.addAttribute("questions", questionService.findQuestionById(gameService.getCurrentQuestionId((long) gameDTO.getTeamScore())));
        return "redirect:/answer/"+id;
    }

    @GetMapping("/answer/{id}")
    public String answer(@PathVariable(value = "id") Long id ,GameDTO gameDTO,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        model.addAttribute("gameDto", gameDTO);
        //TODO
        model.addAttribute("questions", questionService.findQuestionById(gameService.findGameById(id).getCurrentQuestionId()));
        model.addAttribute("teamAnswer", gameService.findGameById(id).getCurrentAnswer());
        return "answer";
    }



    @RequestMapping(value = "/answer",params = "right", method = RequestMethod.POST)
    public String increaseTeamScore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        gameService.increaseTeamScore(gameService.getLastGameId(user.getUsername()));
        return "redirect:/all-games";
    }

    @PostMapping("/answer")
    @RequestMapping(params = "wrong", method = RequestMethod.POST)
    public String increaseViewerScore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        gameService.increaseViewerScore(gameService.getLastGameId(user.getUsername()));
        return "redirect:/all-games";
    }


    @GetMapping("/game-answer")
    public String gameAnswer(Model model, QuestionDTO questionDTO) {
        model.addAttribute("gameDto", new GameDTO());
        System.out.println(questionDTO.getTitle());
        model.addAttribute("question", questionService.loadQuestionByTitle(questionDTO.getTitle()));
        return "game-answer";
    }


}
