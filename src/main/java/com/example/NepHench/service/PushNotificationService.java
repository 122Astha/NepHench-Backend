package com.example.NepHench.service;

import com.example.NepHench.beans.PushNotificationRequest;
import com.example.NepHench.model.Notification;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.NotificationRepository;
import com.example.NepHench.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    private FCMService fcmService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public PushNotificationService(FCMService fcmService,
                                   NotificationRepository notificationRepository, UserRepository userRepository) {
        this.fcmService = fcmService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public String sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "Test Push Notification";
    }

    public List<Notification> getNotificationsByUserId(String user) {
        // Retrieve the User object from the database using the userId
        User users = userRepository.findById(Integer.valueOf(user))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Call the repository method with the retrieved User object
        return notificationRepository.findByUser(users);
    }
}
