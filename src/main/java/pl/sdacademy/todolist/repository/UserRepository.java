package pl.sdacademy.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.sdacademy.todolist.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUsername(String username);
    Optional<User> findUserByPhoneNumber(String phoneNumber);

//    @Query("SELECT u from User u where u.phoneNumber = :phone")
//    Optional<User>findByPhone(@Param("phone") String phone);

}

