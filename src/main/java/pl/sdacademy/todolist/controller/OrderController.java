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
        return "menu";
    }

    @GetMapping({"menu"})
    public String showOptions(Model model) {
        log.info("show menu");
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "menu";
    }

    @GetMapping({"list"})
    public String showListOfOrders(Model model, Principal principal) {
        log.info("list of orders");
        List<Order> orders = orderService.findAllByPhoneNumber(principal.getName());
        model.addAttribute("orders", orders);
        return "list";
    }

    @GetMapping({"orders"})
    public String Orders(Model model) {
        log.info("show orders");
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping({"services"})
    public String Services(Model model) {
        log.info("additional services");
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "services";
    }

    @GetMapping("/addorder")
    public String orderList(Model model, Principal principal) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("order", new Order());
        return "add";
    }

    @PostMapping("/addorder")
    public String addOrder(@Valid OrderDto order, Principal principal) {
        log.info("add task");
        orderService.create(order);
        return "redirect:index";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Order orderToEdit = orderService.find(id);
        model.addAttribute("order", orderToEdit);
        return "edit";
    }

    @PostMapping("edit")
    public String editOrder(@ModelAttribute("order") Order order) {
        orderService.update(order);
        return "redirect:orders";
    }
}
