package pl.sdacademy.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.todolist.entity.Role;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByRole(String role);
}

