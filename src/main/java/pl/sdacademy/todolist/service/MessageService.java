package pl.sdacademy.todolist.service;


import pl.sdacademy.todolist.dto.MessageType;

public interface MessageService {
    void sendMessage(String recipient, String message, MessageType messageType);

}

