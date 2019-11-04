package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.service.UserService;
import pl.sdacademy.todolist.validators.RegisterValidator;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final RegisterValidator registerValidator;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userForm", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute(name = "userForm") UserDto userForm, BindingResult result){
        registerValidator.validate(userForm, result);

        if (result.hasErrors()) {
            return "register";
        }
        userService.create(userForm);
        return "redirect:/login";
    }

}
