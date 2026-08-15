package pl.sdacademy.todolist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sdacademy.todolist.dto.AppointmentDto;
import pl.sdacademy.todolist.dto.MessageType;
import pl.sdacademy.todolist.emailService.EmailService;
import pl.sdacademy.todolist.entity.Appointment;
import pl.sdacademy.todolist.entity.LeaveDate;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.service.AppointmentService;
import pl.sdacademy.todolist.service.LeaveService;
import pl.sdacademy.todolist.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UnloggedUserController {

    private final AppointmentService appointmentService;
    private final LeaveService leaveService;
    private final EmailService emailService;
    private final UserService userService;

    @GetMapping({"/","/scheduleunlogged"})
    public String appointmentSchedule(Model model) {
        List<AppointmentDto> appointmentList = appointmentService.getAppointment();
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("appointments", appointmentList);
        List<LeaveDate> leaveDate = leaveService.getAllLeave();
        model.addAttribute("leave", leaveDate);
        return "scheduler_unlogged";
    }

    @PostMapping("/confirmationunlogged")
    public String appointmentScheduleForm(@ModelAttribute(name = "appointment") Appointment appointment, Model model, @RequestParam String firstName, @RequestParam String phoneNumber) {
        User user = userService.createOrGetUser(phoneNumber);
        appointment.setFirstName(firstName);
        appointment.setUser(user);
        appointmentService.save(appointment);
        int hour = appointment.getAppointmentTime().toLocalTime().getHour();
        emailService.sendMessage("imac@wp.pl", "Masz nowe spotkanie umowione na dzień " + appointment.getAppointmentDate() + ", na godzinę " + hour
                + ".\nImię Klienta: " + appointment.getFirstName() + "\nTelefon: " + user.getPhoneNumber() + "\nWIZYTA UMÓWIONA BEZ LOGOWANIA.", MessageType.MAIL_ADMIN);
        model.addAttribute(appointment);
        return "confirmation";
    }
}
