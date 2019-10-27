package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sdacademy.todolist.entity.Task;
import pl.sdacademy.todolist.service.TaskService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;

    @GetMapping({"/", "index"})
    public String showTasks(Model model) {
        log.info("get tasks list");
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/addtask")
    public String taskList(Model model, Principal principal) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        return "add";
    }

    @PostMapping("addtask")
    public String addTask(@Valid Task task, Principal principal) {
        log.info("add task");
        taskService.create(task);
        return "redirect:index";
    }

    @GetMapping("delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/index";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Task taskToEdit = taskService.find(id);
        model.addAttribute("task", taskToEdit);
        return "edit";
    }

    @PostMapping("edit")
    public String editTask(@ModelAttribute("task") Task task) {
        taskService.update(task);
        return "redirect:index";
    }
}
