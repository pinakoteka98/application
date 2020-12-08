package pl.sdacademy.todolist.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sdacademy.todolist.entity.BindingWorkshop;
import pl.sdacademy.todolist.entity.Nickname;
import pl.sdacademy.todolist.entity.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class OrderDto {

    @Size(min = 2)
    @NotBlank
    private String orderNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfOrder;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate estimatedDate;

    @NotNull
    private Long value;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Nickname nickname;

    @Enumerated(EnumType.STRING)
    private BindingWorkshop bindingWorkshop;

    @NotNull
    private String phoneNumber;

    private String comments;
}



