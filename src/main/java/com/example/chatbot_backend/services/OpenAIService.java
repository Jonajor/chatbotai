package com.example.chatbot_backend.services;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class OpenAIService {
    @Value("${openai.api.key}")
    private String apiKey;

    public String generateResponse(String userMessage) {
        OpenAiService openAiService = new OpenAiService(apiKey);

        // Create a chat request using v1/chat/completions endpoint
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")  // Use GPT-4o-mini
                .messages(Collections.singletonList(new ChatMessage("user", userMessage))) // Correct format
                .maxTokens(100)
                .temperature(0.7)
                .build();

        // Call OpenAI API using createChatCompletion()
        ChatCompletionResult result = openAiService.createChatCompletion(request);

        return result.getChoices().getFirst().getMessage().getContent().trim();
    }
}
