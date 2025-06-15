package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.converter.ChatConverter;
import com.statistictgchatbot.converter.MessageConverter;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.ChatByRealTimeService;
import com.statistictgchatbot.service.ChatService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Service
public class ChatByRealTimeServiceImpl implements ChatByRealTimeService {
    private final ChatService chatService;
    private final MessageConverter messageConverter;
    private final ChatConverter chatConverter;

    public ChatByRealTimeServiceImpl(ChatService chatService,
                                     MessageConverter messageConverter,
                                     @Lazy ChatConverter chatConverter) {
        this.chatService = chatService;
        this.messageConverter = messageConverter;
        this.chatConverter = chatConverter;
    }

    @Override
    public void createOrUpdateChat(String chatId, Message message) throws JsonProcessingException {
        com.statistictgchatbot.model.submodel.Message messageToDb =
                messageConverter.convertMessageTGEntityToDBEntity(message, TypeOfEvent.MESSAGE);

        if(chatService.chatIsExist(chatId)) {
            chatService.appendMessage(chatId, messageToDb);
        }
        else {
            Chat chat = chatConverter.createChat(message, messageToDb);
            chatService.saveChat(chat);
        }
    }
}
