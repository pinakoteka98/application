package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.sdacademy.todolist.dto.MessageType;
import pl.sdacademy.todolist.dto.OrderDto;
import pl.sdacademy.todolist.entity.*;
import pl.sdacademy.todolist.exception.EntityNotFoundException;
import pl.sdacademy.todolist.repository.OrderRepository;
import pl.sdacademy.todolist.repository.RoleRepository;
import pl.sdacademy.todolist.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Validated
public class OrderService {


    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;
    private final SmsService smsService;

    private Map<Long, Order> orders = new ConcurrentHashMap<>();


    public List<Order> findAllByDto(OrderDto orderDto) {
        return orderRepository.findAllByStatus(orderDto.getStatus());
    }

    public List<Order> findAllAsPage() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByPhoneNumber(String phoneNumber) {
        return orderRepository.findAllByPhoneNumber(phoneNumber);
    }

    public Order find(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Transactional
    public Order create(OrderDto orderDto) {
        User user;
        Optional<User> userOptional = userRepository.findUserByPhoneNumber(orderDto.getPhoneNumber());
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            Role role = roleRepository.findByRole("USER");
            user = new User();
            user.setPhoneNumber(orderDto.getPhoneNumber());
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);
        }

        Order orderEntity = new Order();
        orderEntity.setComments(orderDto.getComments());
        orderEntity.setDateOfOrder(orderDto.getDateOfOrder());
        orderEntity.setEstimatedDate(orderDto.getEstimatedDate());
        orderEntity.setOrderNo(orderDto.getOrderNo());
        orderEntity.setPhoneNumber(orderDto.getPhoneNumber());
        orderEntity.setStatus(orderDto.getStatus());
        orderEntity.setValue(orderDto.getValue());
        orderEntity.setUser(user);
        return orderRepository.save(orderEntity);
    }

    @Transactional
    public void update(Order order) {
        Order orderEntity = orderRepository.findById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException(order.getId()));
        Status newStatus = order.getStatus();
        orderEntity.setDateOfOrder(order.getDateOfOrder());
        orderEntity.setEstimatedDate(order.getEstimatedDate());
        orderEntity.setOrderNo(order.getOrderNo());
        orderEntity.setStatus(order.getStatus());
        if (newStatus == Status.READY) {
            smsService.sendMessage(orderEntity.getPhoneNumber(), "Dzień dobry! Miło nam poinformować, że zamówienie numer " + orderEntity.getOrderNo() + " zostało zrealizowane. Zapraszamy po odbiór. Pozdrawiamy i życzymy miłego dnia.", MessageType.SMS_STATUS);
        }
    }

    public void delete(Long id) {
        Order orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        orderRepository.delete(orderEntity);
    }

    public Page<Order> findAllPages(Pageable pageable, String phoneNumber) {
        return orderRepository.findAllByPhoneNumber(pageable, phoneNumber);
    }

    public Page<Order> findAllAsPage(int page, int elementsOnPage, String sortBy, String ascDesc, String phoneNumber) {
        String chooseSortBy;
        switch (sortBy) {
            case "ordernr":
                chooseSortBy = "orderNo";
                break;
            case "expecteddate":
                chooseSortBy = "estimatedDate";
                break;
            case "status":
                chooseSortBy = "status";
                break;
            case "ordervalue":
                chooseSortBy = "value";
                break;
            default:
                chooseSortBy = "dateOfOrder";
        }

        return orderRepository.findAllByPhoneNumber(PageRequest.of(page, elementsOnPage, ascDesc.equals("asc") ? Sort.by(chooseSortBy).ascending() : Sort.by(chooseSortBy).descending()), phoneNumber);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();

    }

    public Page<Order> findAllBySearchText(Integer page, String sortColumn, String ascDesc, String searchText) {
        String chooseSortBy;
        switch (sortColumn) {
            case "ordernr":
                chooseSortBy = "orderNo";
                break;
            case "status":
                chooseSortBy = "status";
                break;
            case "expecteddate":
                chooseSortBy = "estimatedDate";
                break;
            default:
                chooseSortBy = "dateOfOrder";
        }
        return StringUtils.isNotBlank(searchText)
                ? orderRepository.findAllBySearchText(PageRequest.of(page, 5, ascDesc.equals("asc") ? Sort.by(chooseSortBy).ascending() : Sort.by(chooseSortBy).descending()), searchText)
                : orderRepository.findAll(PageRequest.of(page, 5, ascDesc.equals("asc") ? Sort.by(chooseSortBy).ascending() : Sort.by(chooseSortBy).descending()));
    }

    public Double findMiddleOrderValueFromLastYear() {
        LocalDate dateYearAgo = getLocalDateYearAgo();
        return orderRepository.findMiddleOrderValueFromLastYear(dateYearAgo);
    }

    public Integer findNumberOfOrdersFromLastYear() {
        LocalDate dateYearAgo = getLocalDateYearAgo();
        return orderRepository.findNumberOfOrdersFromLastYear(dateYearAgo);
    }

    public Double findAverageMonthlyNumberOfOrdersFromTheLastYear() {
        LocalDate dateYearAgo = getLocalDateYearAgo();
        Integer numberOfOrdersFromLastYear = orderRepository.findNumberOfOrdersFromLastYear(dateYearAgo);
        return (Double) (double) (numberOfOrdersFromLastYear / 12);
    }

    private LocalDate getLocalDateYearAgo() {
        return LocalDate.of(LocalDate.now().getYear() - 1, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
    }

}