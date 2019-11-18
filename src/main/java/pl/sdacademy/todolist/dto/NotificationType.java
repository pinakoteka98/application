package pl.sdacademy.todolist.dto;

import lombok.Getter;

@Getter
public enum NotificationType {

    ORDER_COMPLETED_CONFIRMATION("notification.order.completed");

    private String messageKey;

    NotificationType(String messageKey) {
        this.messageKey = messageKey;
    }
}

