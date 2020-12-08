package pl.sdacademy.todolist.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Entity
@Table (name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id")
    private User user;

}
