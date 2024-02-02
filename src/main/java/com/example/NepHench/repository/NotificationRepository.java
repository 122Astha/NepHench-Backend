package com.example.NepHench.repository;

import com.example.NepHench.model.Notification;
import com.example.NepHench.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser(User user);
}
