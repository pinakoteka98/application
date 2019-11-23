package pl.sdacademy.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.todolist.service.SmsService;

@SpringBootTest
class SmsServiceTests {

    @Autowired
    private SmsService smsService;

    @Test
    void testSendSms() throws Exception {
//
//        // given
//        String recipient = "602271300";
//        String messageBody = "Dzień dobry, zapraszamy po odbiór oprawionych prac.";
//
//        // when
//        smsService.sendMessage(recipient, messageBody);
//
//        // then
//    }
    }
}

