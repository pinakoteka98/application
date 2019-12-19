package pl.sdacademy.todolist.emailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.sdacademy.todolist.dto.MessageType;
import pl.sdacademy.todolist.service.MessageService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService implements MessageService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendMessage(String recipient, String message, MessageType messageType) {
        log.info("Sending message \"{}\" to email {}", message, recipient);
        MimeMessage email = javaMailSender.createMimeMessage();
        Context context;
        String emailBody;
        switch (messageType) {
            case MAIL_REGISTRATION:
                context = new Context();
                context.setVariable("message", message);
                emailBody = this.templateEngine.process("template1.html", context);
                try {
                    MimeMessageHelper mmHelper = setMimeHelper(recipient, email);
                    mmHelper.setSubject("Rejestracja w serwisie Pinakoteka Design");
                    mmHelper.setText(emailBody, true);//true - mogę w tekście przesłać HTML, wyświetli się mailu. mmHelper.setText(content, true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                break;

            case MAIL_RESET_PASSWORD:
                context = new Context();
                context.setVariable("message", message);
                emailBody = this.templateEngine.process("template2.html", context);
                try {
                    MimeMessageHelper mmHelper = setMimeHelper(recipient, email);
                    mmHelper.setSubject("Reset hasła");
                    mmHelper.setText(emailBody, true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                break;

            case MAIL_APPOINTMENT:
                context = new Context();
                context.setVariable("message", message);
                emailBody = this.templateEngine.process("template3.html", context);
                try {
                    MimeMessageHelper mmHelper = setMimeHelper(recipient, email);
                    mmHelper.setSubject("Potwierdzenie spotkania");
                    mmHelper.setText(emailBody, true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                break;

            case MAIL_ADMIN:
                try {
                    MimeMessageHelper mmHelper = new MimeMessageHelper(email);//MimeMessageHelper mmHelper = new MimeMessageHelper(email, true);
                    mmHelper.setTo(recipient);
                    mmHelper.setFrom("rejestracja@pinakoteka.pl");
                    mmHelper.setSubject("Info z serwisu Pinakoteka");
                    mmHelper.setText(message); //true - mogę w tekście przesłać HTML, wyświetli się mailu. mmHelper.setText(content, true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                break;
        }
        javaMailSender.send(email);
    }

    private MimeMessageHelper setMimeHelper(String recipient, MimeMessage email) throws MessagingException {
        MimeMessageHelper mmHelper = new MimeMessageHelper(email, true);
        mmHelper.setTo(recipient);
        mmHelper.setFrom("rejestracja@pinakoteka.pl");
        return mmHelper;
    }
}
