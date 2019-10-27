package pl.sdacademy.todolist.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TaskDto {

    @Size(min = 2)
    @NotBlank
    private String description;
}


