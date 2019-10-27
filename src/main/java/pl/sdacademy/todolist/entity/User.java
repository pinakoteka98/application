package pl.sdacademy.todolist.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false, unique = true)
    private String username;

    @NotNull
    @Column(nullable=false)
    private String firstName;

    @NotNull
    @Column(nullable=false)
    private String password;
}
