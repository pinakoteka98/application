package pl.sdacademy.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private long id;

    public EntityNotFoundException(Long id) {
        super("Enitity with id: " + id + " not found!");
    }
}
