package pl.sdacademy.todolist.dto;

import lombok.Data;

@Data
public class ErrorDto {

    private String message;
    private String exceptionClass;
}
