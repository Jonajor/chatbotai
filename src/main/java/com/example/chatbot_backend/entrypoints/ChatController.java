package com.example.chatbot_backend.entrypoints;

import com.example.chatbot_backend.entities.ChatMessage;
import com.example.chatbot_backend.repositories.ChatMessageRepository;
import com.example.chatbot_backend.services.OpenAIService;
import com.example.chatbot_backend.services.WhatsAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final OpenAIService openAIService;
    private final WhatsAppService whatsAppService;
    private final ChatMessageRepository chatRepository;

    public ChatController(OpenAIService openAIService, WhatsAppService whatsAppService, ChatMessageRepository chatRepository) {
        this.openAIService = openAIService;
        this.whatsAppService = whatsAppService;
        this.chatRepository = chatRepository;
    }

    @PostMapping("/message")
    public ResponseEntity<String> chat(@RequestParam String userMessage, @RequestParam String phoneNumber) {
        String response = openAIService.generateResponse(userMessage);

        // Store message in DB
        ChatMessage chat = new ChatMessage();
        chat.setUserMessage(userMessage);
        chat.setBotResponse(response);
        chat.setTimestamp(LocalDateTime.now());
        chatRepository.save(chat);

        // Send response via WhatsApp
        whatsAppService.sendMessage(phoneNumber, response);

        return ResponseEntity.ok(response);
    }
}
