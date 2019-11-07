package pl.sdacademy.todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.Status;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByPhoneNumber(String phoneNumber);
    List<Order> findAllByStatus(Status status);

    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByPhoneNumber(Pageable pageable, String phoneNumber);
}
