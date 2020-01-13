package spring.controller;


import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import spring.entity.Role;
import spring.entity.User;
import spring.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.service.UserService;



@Slf4j
@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model, UserDTO userDTO) {

        try {
            userService.saveNewUser(User.builder()
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .role(Role.USER)
                    .firstName(userDTO.getFirstName())
                    .surname(userDTO.getSurname())
                    .sex(userDTO.getSex())
                    .age(userDTO.getAge())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .active(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .build());
        } catch (Exception ex) {
            log.info(user.getUsername() + " login is already exist");
            model.addAttribute("message", "login is already exist");
            return "registration";
        }

        return "redirect:/login";


    }
}
