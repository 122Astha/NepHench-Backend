package com.example.NepHench.repository;

import com.example.NepHench.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    Optional<Conversation> findById(Integer id);
}
