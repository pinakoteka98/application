package pl.sdacademy.todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sdacademy.todolist.entity.Order;
import pl.sdacademy.todolist.entity.Status;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByPhoneNumber(String phoneNumber);
    List<Order> findAllByStatus(Status status);

    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByPhoneNumber(Pageable pageable, String phoneNumber);

    @Query("SELECT o FROM Order o WHERE " +
            "o.orderNo LIKE CONCAT('%',:searchText,'%') " +
            "OR o.comments LIKE CONCAT('%',:searchText,'%') " +
            "OR o.dateOfOrder LIKE CONCAT('%',:searchText,'%') " +
            "OR o.estimatedDate LIKE CONCAT('%',:searchText,'%')" +
            "OR o.status LIKE CONCAT('%',:searchText,'%')" +
            "OR o.value LIKE CONCAT('%',:searchText,'%')" +
            "OR o.phoneNumber LIKE CONCAT('%',:searchText,'%')")
    Page<Order> findAllBySearchText(Pageable pageable, String searchText);

    @Query(value = "select sum(value) / count(*)\n" +
            "from orders\n" +
            "where date_of_order between now() - INTERVAL 12 MONTH and now()", nativeQuery = true)
    Double findMiddleOrderValueFromLastYear();

    @Query(value = "select count(*)\n" +
            "from orders\n" +
            "where date_of_order between now() - INTERVAL 12 MONTH and now()", nativeQuery = true)
    Integer findNumberOfOrdersFromLastYear();

    @Query(value = "select count(*) / 12 \n" +
            "from orders\n" +
            "where date_of_order between now() - INTERVAL 12 MONTH and now()", nativeQuery = true)
    Double findAverageMonthlyNumberOfOrdersFromTheLastYear();
}
