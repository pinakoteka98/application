package pl.sdacademy.todolist.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;

    @ManyToOne(optional = false)
//    @Column(name = "user_phone")
    private User user;

    private Date appointmentDate;

    private Time appointmentTime;
}
