package pl.sdacademy.todolist.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.Status;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByPhoneNumber(String phoneNumber);

    List<Order> findAllByStatus(Status status);

    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByPhoneNumber(Pageable pageable, String phoneNumber);
    
    @Query("SELECT o.orderNo FROM Order o WHERE o.id =:id")
    String findOrderNoById(Long id);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT o FROM Order o WHERE " +
            "o.orderNo LIKE CONCAT('%',:searchText,'%') " +
            "OR o.comments LIKE CONCAT('%',:searchText,'%') " +
            "OR o.dateOfOrder LIKE CONCAT('%',:searchText,'%') " +
            "OR o.estimatedDate LIKE CONCAT('%',:searchText,'%')" +
            "OR o.status LIKE CONCAT('%',:searchText,'%')" +
            "OR o.value LIKE CONCAT('%',:searchText,'%')" +
            "OR o.phoneNumber LIKE CONCAT('%',:searchText,'%')")
    Page<Order> findAllBySearchText(Pageable pageable, String searchText);

    @Query("select avg(o.value) from Order o where o.dateOfOrder > :date")
    Double findMiddleOrderValueFromLastYear(LocalDate date);
    
    @Query("select avg(o.value) from Order o where o.dateOfOrder between :dateFrom and :dateTo")
    Double findMiddleOrderValueFromPreviousYear(LocalDate dateFrom, LocalDate dateTo);

    @Query("select count (o) from Order o where o.dateOfOrder > :date")
    Integer findNumberOfOrdersFromLastYear(LocalDate date);
    
    @Query("select count (o) from Order o where o.dateOfOrder between :dateFrom and :dateTo")
    Integer findNumberOfOrdersFromPrevious365Days(LocalDate dateFrom, LocalDate dateTo);

}
