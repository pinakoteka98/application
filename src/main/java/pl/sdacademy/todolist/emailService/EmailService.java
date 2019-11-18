package pl.sdacademy.todolist.emailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
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
    public void sendMessage(String recipient, String message) {
        log.info("Sending message \"{}\" to email {}", message, recipient);
        MimeMessage email = javaMailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("title", "tytuł");
        context.setVariable("description", "opis");
        final String emailBody = this.templateEngine.process("template.html", context);
        try {
//            MimeMessageHelper mmHelper = new MimeMessageHelper(email);
            MimeMessageHelper mmHelper = new MimeMessageHelper(email, true);
            mmHelper.setTo(recipient);
            mmHelper.setFrom("rejestracja@pinakoteka.pl");
            mmHelper.setText(emailBody, true);//true - mogę w tekście przesłać HTML, wyświetli się mailu. mmHelper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(email);
    }
}
