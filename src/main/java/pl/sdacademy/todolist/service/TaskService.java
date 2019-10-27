package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.thymeleaf.util.StringUtils;
import pl.sdacademy.todolist.dto.PageDto;
import pl.sdacademy.todolist.entity.Task;
import pl.sdacademy.todolist.exception.EntityNotFoundException;
import pl.sdacademy.todolist.repository.TaskRepository;
import pl.sdacademy.todolist.repository.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Validated
public class TaskService {


    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    private Map<Long, Task> tasks = new ConcurrentHashMap<>();


    public List<Task> findAllByDto(PageDto pageDto) {
        Sort.Direction sortDirection = Sort.Direction.fromString(pageDto.getSortDirection());
        Sort sort = Sort.by(sortDirection, pageDto.getSortColumn());
        Pageable page = PageRequest.of(pageDto.getPageIndex(), pageDto.getPageSize(), sort);
        if (StringUtils.isEmpty(pageDto.getFilter())) {
            return taskRepository.findAll(page).getContent();
        }
        return taskRepository.findAllByDescriptionLike("%" + pageDto.getFilter() + "%", page);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task find(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task update(Task task) {
        Task updatedTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new EntityNotFoundException(task.getId()));
        updatedTask.setDescription(task.getDescription());
        updatedTask.setTimeLimit(task.getTimeLimit());
        updatedTask.setFinished(task.isFinished());
        updatedTask.setPriority(task.getPriority());
        taskRepository.save(updatedTask);
        return updatedTask;
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        taskRepository.delete(task);
    }

}
