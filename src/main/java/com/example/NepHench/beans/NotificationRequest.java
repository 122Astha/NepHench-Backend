package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NotificationRequest {
    private String user;
    private String content;
    private Boolean isRead;
    private Instant timestamp;

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
