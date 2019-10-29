package pl.sdacademy.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.Status;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserUsername(String username);
    List<Order> findAllByStatus(Status status);

}
