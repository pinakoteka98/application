package pl.sdacademy.todolist.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    @NotBlank
    @Min(9)
    @Max(9)
    private String phoneNumber;

    @NotBlank
    @Min(6)
    @Max(255)
    private String password;

    @NotBlank
    @Email
    @Min(6)
    @Max(255)
    private String email;

}
