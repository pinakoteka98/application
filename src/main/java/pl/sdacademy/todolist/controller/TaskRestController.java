package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.todolist.dto.PageDto;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders/")
public class TaskRestController {

    private final OrderService orderService;


//    @GetMapping
//    public List<Order> findAll(@Valid PageDto page) {
//        return orderService.findAllByDto(page);
//    }

    @GetMapping("/{id}")
    public Order findOrder(@PathVariable Long id) {
        return orderService.find(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Order addOrder(@Valid @RequestBody Order order) {
        return orderService.create(order);
    }

    @PutMapping("/{id}")
    public Order order(@Valid @RequestBody Order order, @PathVariable Long id) {
        order.setId(id);
        return orderService.update(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
    }
}

