package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.todolist.entity.Appointment;
import pl.sdacademy.todolist.repository.AppointmentRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/services")
public class ScheduleRestController {

    private final AppointmentRepository appointmentRepository;

    @PostMapping("/add")
    public @ResponseBody
    String addNewAppointment(@ModelAttribute Appointment appointment){
        try {
            appointmentRepository.save(appointment);
            return "Appointment was added succesfully";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

    @GetMapping("/all")
    public @ResponseBody
    List<Appointment> getAllAppointments(){
        return sortAppointments(appointmentRepository.findAll());
    }

    @GetMapping("/all/{date}")
    public @ResponseBody List<Appointment> getAppointmentsByDate(@PathVariable String date){
        try{
            Date sqlDate = Date.valueOf(date);
            return sortAppointments(appointmentRepository.findAllByAppointmentDate(sqlDate));
        } catch (Exception ex){
            Appointment appt = new Appointment();
            List<Appointment> appointments = new ArrayList<>();
            appointments.add(appt);
            return appointments;
        }
    }

    private List<Appointment> sortAppointments(List<Appointment> appointments){
        appointments.sort(Comparator.comparing(Appointment::getAppointmentDate)
                .thenComparing(Appointment::getAppointmentTime));
        return appointments;
    }
}
