package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.todolist.dto.OrderDto;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.emailService.EmailService;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.Status;
import pl.sdacademy.todolist.service.OrderService;
import pl.sdacademy.todolist.service.SMSService;
import pl.sdacademy.todolist.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class OrderController {

    private final OrderService orderService;
    private final SMSService smsService;
    private final UserService userService;

    @GetMapping({"/", "index"})
    public String showOrders(Model model) {
        log.info("get order list");
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "login";
    }

    @GetMapping("/menu")
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

    @GetMapping("orders")
    public String Orders(@RequestParam Integer page,
                         @RequestParam(name = "sortcolumn") String sortColumn,
                         @RequestParam(name = "ascdesc") String ascDesc,
                         @RequestParam(name = "searchtext", required = false) String searchText,
                         Model model) {
        log.info("show orders");
        Page<Order> orderPage = orderService.findAllBySearchText(page, sortColumn, ascDesc, searchText);
        long size = orderService.findAll().stream().filter(e -> e.getStatus() == Status.INPROGRESS).count();
        int currentPage = orderPage.getNumber();
        int totalPages = orderPage.getTotalPages();
        List<Order> orders = orderPage.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortcolumn", sortColumn);
        model.addAttribute("ascdesc", ascDesc);
        model.addAttribute("searchtext", searchText);
        model.addAttribute("size", size);
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
        return "redirect:orders";

    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Order orderToEdit = orderService.find(id);
        model.addAttribute("order", orderToEdit);
        return "edit";
    }

    @PostMapping("edit")
    public String editOrder(@ModelAttribute("id") UserDto userForm, Order order) {
        orderService.update(order);
//        smsService.sendMessage(userForm.getPhoneNumber(), "Dzień dobry, zapraszamy po odbiór oprawionych prac.");
        return "redirect:orders";
    }

}
