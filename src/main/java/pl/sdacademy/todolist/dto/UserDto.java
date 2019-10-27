package pl.sdacademy.todolist.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    @NotBlank
    @Max(255)
    private String firstName;

    @NotBlank
    @Min(4)
    @Max(255)
    private String username;

    @NotBlank
    @Min(6)
    @Max(255)
    private String password;

}
