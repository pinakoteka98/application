package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sdacademy.todolist.dto.OrderDto;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.service.OrderService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping({"/", "index"})
    public String showOrders(Model model) {
        log.info("get tasks list");
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "index";
    }

    @GetMapping({"menu"})
    public String showOptions(Model model) {
        log.info("show menu");
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "menu";
    }

    @GetMapping("/addorder")
    public String taskList(Model model, Principal principal) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("order", new Order());
        return "add";
    }

    @PostMapping("/addorder")
    public String addTask(@Valid OrderDto order, Principal principal) {
        log.info("add task");
        orderService.create(order);
        return "redirect:index";
    }
}
