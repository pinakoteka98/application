package pl.sdacademy.todolist.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sdacademy.todolist.dto.ErrorDto;

import javax.validation.ConstraintViolationException;
import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public ErrorDto handleNotFoundException(EntityNotFoundException e){
        log.error("Exception handled", e);
        ErrorDto error=new ErrorDto();
        error.setExceptionClass(e.getClass().getCanonicalName());
        error.setMessage(e.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ErrorDto handleValidationException(Exception e){
        log.error("Exception handled", e);
        ErrorDto error=new ErrorDto();
        error.setExceptionClass(e.getClass().getCanonicalName());
        error.setMessage(e.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorDto handleGeneralException(Exception e){
        log.error("Exception handled", e);
        ErrorDto error=new ErrorDto();
        error.setExceptionClass(e.getClass().getCanonicalName());
        error.setMessage(e.getMessage());
        return error;
    }
}
