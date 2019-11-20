package pl.sdacademy.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.todolist.emailService.EmailService;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() throws Exception {
//        // given
//        String recipient = "imac@wp.pl";
//        String message = "Test email message";
//
//        // when
//        emailService.sendMessage(recipient, message);
//
//        // then
//    }
    }
}

