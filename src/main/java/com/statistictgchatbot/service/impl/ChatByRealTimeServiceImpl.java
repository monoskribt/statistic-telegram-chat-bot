package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.converter.MessageConverter;
import com.statistictgchatbot.exception.ChatNotFoundException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.ChatByRealTimeService;
import com.statistictgchatbot.service.ChatService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ChatByRealTimeServiceImpl implements ChatByRealTimeService {
    private final ChatService chatService;
    private final MessageConverter messageConverter;

    public ChatByRealTimeServiceImpl(ChatService chatService, MessageConverter messageConverter) {
        this.chatService = chatService;
        this.messageConverter = messageConverter;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void getOrSaveChat(String chatId, Message message) throws JsonProcessingException {
        try {
            Chat chat = chatService.getChatByChatId(message.getChatId().toString());
            if(message.hasText()) {
                com.statistictgchatbot.model.submodel.Message messsageToDb =
                        messageConverter.convertMessageTGEntityToDBEntity(message, TypeOfEvent.MESSAGE);
                chat.setMessages(new ArrayList<>(Collections.singleton(messsageToDb)));
                chat.setId(chatService.createIdHashIdForChat(chat, objectMapper));
                chatService.saveChat(chat);
            }
        } catch (ChatNotFoundException | JsonProcessingException chatNotFoundException) {
            Chat chat = new Chat();
            chat.setChatId(message.getChatId().toString());
            chat.setChatName(message.getChat().getTitle());

            com.statistictgchatbot.model.submodel.Message messsageToDb =
                    messageConverter.convertMessageTGEntityToDBEntity(message, TypeOfEvent.MESSAGE);

            chat.setMessages(new ArrayList<>(Collections.singleton(messsageToDb)));

            chat.setId(chatService.createIdHashIdForChat(chat, objectMapper));
            chatService.saveChat(chat);
        }
    }
}
