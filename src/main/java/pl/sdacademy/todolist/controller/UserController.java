package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.emailService.EmailService;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.service.OrderService;
import pl.sdacademy.todolist.service.UserService;
import pl.sdacademy.todolist.utils.AppUtils;
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
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute(name = "userForm") UserDto userForm, BindingResult result, Model model) {
        Optional<User> existing = userService.findByPhoneNumber(userForm.getPhoneNumber());
        registerValidator.validate(userForm, result);
        registerValidator.validatePhoneExist(existing, result);

        if (result.hasErrors()) {
            return "register";
        }
        userService.create(userForm);
        emailService.sendEmail(userForm.getEmail(), "Potwierdzenie rejestracji w serwisie Pinakoteka Design.", "Dziękujemy za rejestrację w naszym serwisie.\nJeśli nie dokonywałeś rejestracji napisz do nas o tym w informacji zwrotnej.");
        model.addAttribute("info", "Rejestracja zakończona sukcesem. Możesz się zalogować.");
        model.addAttribute("login", userForm.getPhoneNumber());
        return "login";
    }

    @GetMapping({"/list/{page}"})
    public String showListOfOrders(@PathVariable Integer page, Model model, Principal principal) {
        log.info("list of orders");
        Page<Order> ordersPage = orderService.findAllAsPage(page, 5, "dateOfOrder", "asc", principal.getName());
        List<Order> ordersList = ordersPage.getContent();
        int currentPage = ordersPage.getNumber();
        int totalPages = ordersPage.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("orders", ordersList);
        model.addAttribute("totalPages", totalPages);
        return "list";
    }

    @GetMapping("/list/{page}/sort")
    public String showSortedValues(@PathVariable Integer page, @RequestParam(name = "sortcolumn") String sortColumn, @RequestParam String ascdesc, @RequestParam Integer elements, Principal principal, Model model) {
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

    @GetMapping("resetpassword")
    public String showResetPage(){
        return "resetpassword";
    }

    @PostMapping("resetpassword")
    public String resetPassword(@RequestParam String phoneNumber, @RequestParam String email, Model model){
        Optional<User> userOptional = userService.findByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if (user.getEmail().equals(email)){
                String newPassword = AppUtils.generatePassword();
                user.setPassword(passwordEncoder.encode(newPassword));
                userService.uptade(user);
                model.addAttribute("login", phoneNumber);
                model.addAttribute("info", "Nowe hasło zostało wysłane na Twój adres email. Możesz się nim zalogować.");
                emailService.sendEmail(email, "Twoje hasło zostało zresetowane", "Twoje nowe hasło to:\n"+newPassword);
                return "login";
            }
        }
        model.addAttribute("info", "Brak konta użytkownika w bazie dla wskazanego adresu email i nr telefonu");
        return "resetpassword";
    }

}
