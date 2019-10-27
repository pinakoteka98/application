package pl.sdacademy.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.todolist.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}

