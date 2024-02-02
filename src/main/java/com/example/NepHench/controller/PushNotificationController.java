package com.example.NepHench.controller;

import com.example.NepHench.beans.NotificationRequest;
import com.example.NepHench.beans.PushNotificationRequest;
import com.example.NepHench.exception.NotFoundException;
import com.example.NepHench.model.Notification;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.NotificationRepository;
import com.example.NepHench.repository.UserRepository;
import com.example.NepHench.service.BookingService;
import com.example.NepHench.service.PushNotificationService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class PushNotificationController {
    private PushNotificationService pushNotificationService;

    private BookingService bookingService;

    @Autowired
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;
    public PushNotificationController(PushNotificationService pushNotificationService, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.pushNotificationService = pushNotificationService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/notification/{user}")
    public List<Notification> getNotificationsByUserId(@PathVariable("user") String user) {
        // Call a service or repository method to fetch notifications by userId
        return pushNotificationService.getNotificationsByUserId(user);
    }
    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) throws IOException {
//        pushNotificationService.sendPushNotificationToToken(request);
//        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpRequest = new HttpPost("https://exp.host/--/api/v2/push/send");

        // Set the Authorization header with the Server Key
//        httpRequest.setHeader("Authorization", "key=AAAAmjDqQO4:APA91bG4dtyMPTwa7RQdJ8WIpa-pWMqbMqa-apAkbm2ND5VNSDCMQ87gy8uQZWg2kfdh099SohK-7mZt0e15F7Q-_C19f8bU6dMQalSvTOCaPn3V-D8MW4OdyOX47Xsv5Qmgt_nupWBw");
        httpRequest.setHeader("Content-Type", "application/json");

        // Construct the notification payload
        String payload = "{"
                + "\"to\": \"" + request.getDeviceToken() + "\","
                + "\"title\": \"" + request.getTitle() + "\","
                + "\"body\": \"" + request.getMessage() + "\""
                + "}";

        // Set the payload as the request body
        StringEntity entity = new StringEntity(payload);
        httpRequest.setEntity(entity);

        // Send the request and retrieve the response
        HttpResponse response = httpClient.execute(httpRequest);

        // Handle the response
        int statusCode = response.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(response.getEntity());
        // Process the response as needed based on the statusCode and responseString

        return ResponseEntity.ok().body(responseString);
    }

    @PostMapping("/notifications")
    public void receiveNotification(@RequestBody NotificationRequest notification) {
        Optional<User> user = userRepository.findById(Integer.valueOf(notification.getUser()));
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        Instant currentTimeStamp = Instant.now();
        Notification notification1 = new Notification();
        notification1.setUser(user.get());
        notification1.setContent(notification.getContent());
        notification1.setTimestamp(currentTimeStamp);
        notification1.setIsRead(false);

        // Call the notificationService to store the data in the database
        notificationRepository.save(notification1);
    }
}
