package com.example.NepHench.controller;

import com.example.NepHench.beans.ConversationRequest;
import com.example.NepHench.beans.UnauthorizedException;
import com.example.NepHench.exception.NotFoundException;
import com.example.NepHench.model.Conversation;
import com.example.NepHench.model.Message;
import com.example.NepHench.repository.ConversationRepository;
import com.example.NepHench.repository.MessageRepository;
import com.example.NepHench.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ConversationController(ConversationRepository conversationRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/{conversationId}/messages")
    public List<Message> getChatHistory(@PathVariable Integer conversationId)  {
        // Find the conversation by ID
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("Conversation not found"));

        // Retrieve all messages for the conversation
        return messageRepository.findByConversationOrderByTimestampAsc(conversation);
    }
    @PostMapping
    public Conversation createConversation(@RequestBody ConversationRequest request) {
        Conversation convo = new Conversation();
        convo.setUser1(request.getUser1());
        convo.setUser2(request.getUser2());
        return conversationRepository.save(convo);
    }

    @PostMapping("/{conversationId}/messages/{sender}")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Integer conversationId,
            @PathVariable String sender,
            @RequestBody Message message
    ) throws UnauthorizedException, BadRequestException {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("Conversation not found"));

        message.setConversation(conversation);
        // Set the sender user based on the provided sender identifier
        if (sender.equals("user1")) {
            message.setSender(conversation.getUser1());
        } else if (sender.equals("user2")) {
            message.setSender(conversation.getUser2());
        } else {
            throw new BadRequestException("Invalid sender");
        }

        message.setTimestamp(Instant.now());

        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

}

