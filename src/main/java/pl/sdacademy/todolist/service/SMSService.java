package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.sdacademy.todolist.entity.Sms;
import pl.sdacademy.todolist.repository.SmsRepository;
import pl.smsapi.BasicAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.SmsapiException;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SMSService implements MessageService {

    private final SmsRepository smsRepository;

    @Value("${api.sms.client.login}")
    private String login;

    @Value("${api.sms.client.password}")
    private String password;

    @Override
    public void sendMessage(String recipient, String message) {
        log.info("Sending SMS message \"{}\" to phone {}", message, recipient);
        LocalDateTime startNotSendPeriod = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 21, 0);
        LocalDateTime endNotSendPeriod = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth() + 1, 10, 0);
        if (LocalDateTime.now().isAfter(startNotSendPeriod) && LocalDateTime.now().isBefore(endNotSendPeriod)) {
            Sms sms = new Sms();
            sms.setPhoneNumber(recipient);
            sms.setMessage(message);
            saveSMS(sms);
        } else {
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
                Sms sms = new Sms();
                sms.setPhoneNumber(recipient);
                sms.setMessage(message);
                saveSMS(sms);
            }
        }
    }

    public void saveSMS(Sms sms) {
        smsRepository.save(sms);
    }

    @Scheduled(cron = "0 0 10-19 * * MON-FRI")
    public void resendUndeliveredMessages() {
        List<Sms> allSms = smsRepository.findAll();
        allSms.forEach(sms -> {
            sendMessage(sms.getPhoneNumber(), sms.getMessage());
            log.info("Wysłano sms o treści \"{}\" na nr telefonu: {}", sms.getMessage(), sms.getPhoneNumber());
            smsRepository.delete(sms);
        });
        log.info("Sprawdzenie w bazie czy istnieją smsy do wysłania oraz ich wysłanie");
    }

//    @Override
//    public void sendMessage(String recipient, String message) {
//        log.info("Sending SMS message \"{}\" to phone {}", message, recipient);
//
//        try {
//            BasicAuthClient client = new BasicAuthClient(login, password);
//
//            SmsFactory smsApi = new SmsFactory(client);
////            String phoneNumber = "602271300";
//            SMSSend action = smsApi.actionSend()
//                    .setText(message)
//                    .setTo(recipient);
//
//            StatusResponse result = action.execute();
//
//            for (MessageResponse status : result.getList()) {
//                log.info("sms status: {} {}", status.getNumber(), status.getStatus());
//            }
//
//        } catch (SmsapiException e) {
//            log.error("Error when sending SMS message", e);
//        }
//    }
}

