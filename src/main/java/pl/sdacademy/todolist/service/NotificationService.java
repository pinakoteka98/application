package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import pl.sdacademy.todolist.dto.MessageDto;
import pl.sdacademy.todolist.dto.UserDto;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final MessageSource messageSource;
    private final MessageService emailService;
    private final MessageService smsService;

    public void notify(MessageDto message) {
        UserDto recipient = message.getRecipient();
        String messageBody = messageSource.getMessage(message.getNotificationType().getMessageKey(), null, Locale.getDefault());
        switch (message.getMessageType()) {
            case EMAIL:
                emailService.sendMessage(recipient.getEmail(), messageBody);
                break;
            case SMS:
                smsService.sendMessage(recipient.getPhoneNumber(), messageBody);
                break;
        }

        throw new IllegalArgumentException("Message type not supported");
    }
}

