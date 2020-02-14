package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.dto.QuestionDTO;
import spring.service.GameService;
import spring.service.QuestionService;


@Controller
public class QuestionController {

    private QuestionService questionService;
    private GameService gameService;

    @Autowired
    public QuestionController(QuestionService questionService, GameService gameService) {
        this.questionService = questionService;
        this.gameService = gameService;
    }

    @GetMapping("/questions")
    public String getAllQuestions(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "questions";
    }

    @GetMapping("/config")
    public String question(Model model) {
        model.addAttribute("questionDto", new QuestionDTO());
        model.addAttribute("games", gameService.getAllGames());
        return "config";
    }

    @PostMapping("/config")
    public String addQuestions(QuestionDTO questionDTO, Model model) {
        try {
            questionService.saveNewQuestion(questionDTO);
        } catch (Exception ex) {
            model.addAttribute("message", "Question is already exist");
            return "redirect:/config";
        }
        return "redirect:/config";
    }

}
