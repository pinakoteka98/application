package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sdacademy.todolist.dto.OrderDto;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.service.OrderService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TaskController {

    private final OrderService taskService;

//    @GetMapping({"/", "index"})
//    public String showTasks(Model model) {
//        log.info("get tasks list");
//        List<Order> tasks = taskService.findAll();
//        model.addAttribute("tasks", tasks);
//        return "index";
//    }


}