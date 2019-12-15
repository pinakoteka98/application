package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.todolist.dto.OrderDto;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.Status;
import pl.sdacademy.todolist.service.OrderService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class OrderController {

    private final OrderService orderService;

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

    @GetMapping("orders")
    public String Orders(@RequestParam(required = false) Integer page,
                         @RequestParam(name = "sortcolumn", required = false) String sortColumn,
                         @RequestParam(name = "ascdesc", required = false) String ascDesc,
                         @RequestParam(name = "searchtext", required = false) String searchText,
                         Model model) {
        log.info("show orders");
        Page<Order> orderPage = orderService.findAllBySearchText(page == null ? 0 : page, sortColumn == null ? "orderdate" : sortColumn, ascDesc == null ? "desc" : ascDesc, searchText);
        long size = orderService.findAll().stream().filter(e -> e.getStatus() == Status.INPROGRESS).count();
        int currentPage = orderPage.getNumber();
        int totalPages = orderPage.getTotalPages();
        double averageOrderValueFromLastYear = orderService.findMiddleOrderValueFromLastYear() == null ? 0 : orderService.findMiddleOrderValueFromLastYear();
        int numberOfOrdersFromLastYear = orderService.findNumberOfOrdersFromLastYear();
        double averageMonthlyNumberOfOrdersFromTheLastYear = orderService.findAverageMonthlyNumberOfOrdersFromTheLastYear();
        List<Order> orders = orderPage.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortcolumn", sortColumn);
        model.addAttribute("ascdesc", ascDesc);
        model.addAttribute("searchtext", StringUtils.isNotBlank(searchText) ? searchText : "");
        model.addAttribute("size", size);
        model.addAttribute("averageOrderValueFromLastYear", averageOrderValueFromLastYear);
        model.addAttribute("numberOfOrdersFromLastYear", numberOfOrdersFromLastYear);
        model.addAttribute("averageMonthlyNumberOfOrdersFromTheLastYear", averageMonthlyNumberOfOrdersFromTheLastYear);
        return "orders";
    }

    @GetMapping("/addorder")
    public String orderList(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        Order order = new Order();
        order.setDateOfOrder(LocalDate.now());
        order.setEstimatedDate(LocalDate.now().plusDays(7));
        model.addAttribute("order", order);
        return "add";
    }

    @PostMapping("/addorder")
    public String addOrder(@Valid OrderDto order) {
        log.info("add task");
        orderService.create(order);
        return "redirect:orders";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Order orderToEdit = orderService.find(id);
        model.addAttribute("order", orderToEdit);
        return "edit";
    }

    @PostMapping("/edit")
    public String editOrder(@ModelAttribute(name = "order") Order order) {
        orderService.update(order);
        return "redirect:orders";
    }
}
