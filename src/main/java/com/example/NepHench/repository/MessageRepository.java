package com.example.NepHench.repository;

import com.example.NepHench.model.Conversation;
import com.example.NepHench.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByConversationOrderByTimestampAsc(Conversation conversation);
}

