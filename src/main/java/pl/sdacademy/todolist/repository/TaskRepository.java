package pl.sdacademy.todolist.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.todolist.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserUsername(String username);

    List<Task> findAllByDescriptionLike(String filter, Pageable page);

}


