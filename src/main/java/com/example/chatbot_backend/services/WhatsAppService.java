package com.example.chatbot_backend.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    private static final String ACCOUNT_SID = "";
    private static final String AUTH_TOKEN = "";
    private static final String FROM_WHATSAPP = "whatsapp:+";  // Twilio Sandbox Number

    public void sendMessage(String to, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        // Ensure the phone number is in correct E.164 format
        to = to.replaceAll("\\s+", ""); // Remove spaces
        if (!to.startsWith("whatsapp:+")) {
            to = "whatsapp:+" + to.replaceAll("[^0-9]", "");  // Ensure correct format
        }

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(FROM_WHATSAPP),  // Twilio WhatsApp number
                message
        ).create();
    }
}
