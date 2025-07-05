package com.statistictgchatbot.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.ChatService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class ChatConverter {
    private final ChatService chatService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatConverter(ChatService chatService) {
        this.chatService = chatService;
    }

    public Chat createChat(Message message, com.statistictgchatbot.model.submodel.Message messageToDb) throws JsonProcessingException {
        Chat chat = new Chat();
        chat.setChatId(message.getChatId().toString());
        chat.setChatName(message.getChat().getTitle());

        chat.setMessages(new ArrayList<>(Collections.singleton(messageToDb)));

        chat.setId(chatService.createHashIdForChat(chat, objectMapper));
        return chat;
    }
}
