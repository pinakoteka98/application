package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.smsapi.BasicAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.SmsapiException;

@RequiredArgsConstructor
@Slf4j
@Service
public class SMSService implements MessageService {

//    @Value("${api.sms.client.login}")
    private String login;

//    @Value("${api.sms.client.password}")
    private String password;

    @Override
    public void sendMessage(String recipient, String message) {
        log.info("Sending SMS message \"{}\" to phone {}", message, recipient);

        try {
            BasicAuthClient client = new BasicAuthClient(login, password);

            SmsFactory smsApi = new SmsFactory(client);
//            String phoneNumber = "602271300";
            SMSSend action = smsApi.actionSend()
                    .setText(message)
                    .setTo(recipient);

            StatusResponse result = action.execute();

            for (MessageResponse status : result.getList()) {
                log.info("sms status: {} {}", status.getNumber(), status.getStatus());
            }

        } catch (SmsapiException e) {
            log.error("Error when sending SMS message", e);
        }
    }
}

