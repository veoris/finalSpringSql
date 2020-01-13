package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.dto.QuestionDTO;
import spring.dto.UserDTO;
import spring.entity.Question;
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
        //TODO delete first model.addAttribute?
        model.addAttribute("questionDto", new QuestionDTO());
        model.addAttribute("games", gameService.getAllGames());
        return "config";
    }

    @PostMapping("/config")
    public String addQuestions(QuestionDTO questionDTO, Model model) {
        try {
            questionService.saveNewQuestion(Question.builder()
                    .title(questionDTO.getTitle())
                    .description(questionDTO.getDescription())
                    .answer(questionDTO.getAnswer())
                    .help(questionDTO.getHelp())
                    .gameId(questionDTO.getGameId())
                    .answered(false)
                    .build()
            );

        } catch (Exception ex) {

            System.out.println("-----------------------------------------------------------------------------");
            model.addAttribute("message", "Question is already exist");
            return "config";
        }

        return "redirect:/config";
    }

}
