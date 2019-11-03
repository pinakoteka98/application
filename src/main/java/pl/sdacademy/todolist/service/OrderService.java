package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.thymeleaf.util.StringUtils;
import pl.sdacademy.todolist.dto.OrderDto;
import pl.sdacademy.todolist.dto.PageDto;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.exception.EntityNotFoundException;
import pl.sdacademy.todolist.repository.OrderRepository;
import pl.sdacademy.todolist.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Validated
public class OrderService {


    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private Map<Long, Order> tasks = new ConcurrentHashMap<>();


    public List<Order> findAllByDto(OrderDto orderDto) {
        return orderRepository.findAllByStatus(orderDto.getStatus());
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order find(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Order create(OrderDto orderDto) {
        Order orderEntity = new Order();
        orderEntity.setComments(orderDto.getComments());
        orderEntity.setDateOfOrder(orderDto.getDateOfOrder());
        orderEntity.setEstimatedDate(orderDto.getEstimatedDate());
        orderEntity.setOrderNo(orderDto.getOrderNo());
        orderEntity.setPhoneNumber(orderDto.getPhoneNumber());
        orderEntity.setStatus(orderDto.getStatus());
        orderEntity.setValue(orderDto.getValue());
        return orderRepository.save(orderEntity);
    }

    @Transactional
    public Order update(Order order) {
        Order orderEntity = orderRepository.findById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException(order.getId()));
        orderEntity.setComments(order.getComments());
        orderEntity.setDateOfOrder(order.getDateOfOrder());
        orderEntity.setEstimatedDate(order.getEstimatedDate());
        orderEntity.setOrderNo(order.getOrderNo());
        orderEntity.setPhoneNumber(order.getPhoneNumber());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setValue(order.getValue());
        return orderRepository.save(orderEntity);
    }

    public void delete(Long id) {
        Order task = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        orderRepository.delete(task);
    }

}