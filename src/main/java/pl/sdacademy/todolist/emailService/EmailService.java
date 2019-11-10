package pl.sdacademy.todolist.emailService;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String content) {
        MimeMessage email = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mmHelper = new MimeMessageHelper(email);//MimeMessageHelper mmHelper = new MimeMessageHelper(email, true);
            mmHelper.setTo(to);
            mmHelper.setFrom("pinakotekagdansk@gmail.com");
            mmHelper.setSubject(subject);
            mmHelper.setText(content); //true - mogę w tekście przesłać HTML, wyświetli się mailu. mmHelper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(email);
    }
}
