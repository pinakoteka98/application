package pl.sdacademy.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sdacademy.todolist.dto.MessageDto;

@Controller
@RequestMapping({"", "/message"})
public class MessageController {

    @GetMapping
    public String sendMessage(Model model) {
        model.addAttribute("message", new MessageDto());
        return "sendMessage";
    }

    @PostMapping
    public String sendMessage(@ModelAttribute MessageDto message) {
        return "redirect:/";
    }
}

