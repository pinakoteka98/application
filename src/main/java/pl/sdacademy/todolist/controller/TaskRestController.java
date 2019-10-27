package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.todolist.dto.PageDto;
import pl.sdacademy.todolist.entity.Task;
import pl.sdacademy.todolist.service.TaskService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks/")
public class TaskRestController {

    private final TaskService taskService;


    @GetMapping
    public List<Task> findAll(@Valid PageDto page) {
        return taskService.findAllByDto(page);
    }

    @GetMapping("/{id}")
    public Task findTask(@PathVariable Long id) {
        return taskService.find(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Task addTask(@Valid @RequestBody Task task) {
        return taskService.create(task);
    }

    @PutMapping("/{id}")
    public Task task(@Valid @RequestBody Task task, @PathVariable Long id) {
        task.setId(id);
        return taskService.update(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }
}

