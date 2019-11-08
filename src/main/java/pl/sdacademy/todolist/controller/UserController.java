package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.service.OrderService;
import pl.sdacademy.todolist.service.UserService;
import pl.sdacademy.todolist.validators.RegisterValidator;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final RegisterValidator registerValidator;
    private final OrderService orderService;

    @GetMapping(value = "/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userForm", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute(name = "userForm") UserDto userForm, BindingResult result){
        Optional<User> existing = userService.findByPhoneNumber(userForm.getPhoneNumber());
        registerValidator.validate(userForm, result);
        registerValidator.validatePhoneExist(existing, result);

        if (result.hasErrors()) {
            return "register";
        }
        userService.create(userForm);
        return "redirect:/login";
    }

    @GetMapping({"/list/{page}"})
    public String showListOfOrders(@PathVariable Integer page, Model model, Principal principal) {
        log.info("list of orders");
        Page<Order> ordersPage = orderService.findAllAsPage(page, 5, "ordernr", "asc", principal.getName());
        List<Order> ordersList = ordersPage.getContent();
        int currentPage = ordersPage.getNumber();
        int totalPages = ordersPage.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("orders", ordersList);
        model.addAttribute("totalPages", totalPages);
        return "list";
    }

    @GetMapping("/list/{page}/sort")
    public String showSortedValues(@PathVariable Integer page, @RequestParam(name = "sortcolumn") String sortColumn, @RequestParam String ascdesc, @RequestParam Integer elements,Principal principal, Model model){
        Page<Order> ordersPage = orderService.findAllAsPage(page, elements, sortColumn, ascdesc, principal.getName());
        List<Order> ordersList = ordersPage.getContent();
        int currentPage = ordersPage.getNumber();
        int totalPages = ordersPage.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("orders", ordersList);
        model.addAttribute("sortcolumn", sortColumn);
        model.addAttribute("ascdesc", ascdesc);
        model.addAttribute("elements", elements);
        model.addAttribute("totalPages", totalPages);
        return "list";
    }

}
