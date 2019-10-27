package pl.sdacademy.todolist.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@Table (name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min=3)
    private String description;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date timeLimit;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private boolean isFinished;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id")
    private User user;

}
