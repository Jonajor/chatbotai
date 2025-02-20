package com.example.chatbot_backend.repositories;

import com.example.chatbot_backend.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> { }

