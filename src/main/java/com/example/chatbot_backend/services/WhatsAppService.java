package com.example.chatbot_backend.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${twilio.sid}")
    private String sid;

    @Value("${twilio.token}")
    private String token;

    @Value("${twilio.sandbox}")
    private String sandbox;

    private static final String FROM_WHATSAPP = "whatsapp:+";  // Twilio Sandbox Number

    public void sendMessage(String to, String message) {
        Twilio.init(sid, token);
        // Ensure the phone number is in correct E.164 format
        to = to.replaceAll("\\s+", ""); // Remove spaces
        if (!to.startsWith("whatsapp:+")) {
            to = "whatsapp:+" + to.replaceAll("[^0-9]", "");  // Ensure correct format
        }

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(FROM_WHATSAPP.concat(sandbox)),  // Twilio WhatsApp number
                message
        ).create();
    }
}
