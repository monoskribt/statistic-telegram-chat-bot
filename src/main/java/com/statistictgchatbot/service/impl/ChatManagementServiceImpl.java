package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.converter.ChatConverter;
import com.statistictgchatbot.exception.ChatNotFoundException;
import com.statistictgchatbot.exception.ParseFileException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.ChatManagementService;
import com.statistictgchatbot.service.ChatService;
import com.statistictgchatbot.service.MessageSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

import static com.statistictgchatbot.constant.MessageToUser.*;
import static com.statistictgchatbot.constant.MessageToUser.SUCCESSFULLY_SAVED;

@Service
@RequiredArgsConstructor
public class ChatManagementServiceImpl implements ChatManagementService {

    private final ChatService chatService;
    private final ChatConverter chatConverter;

    @Override
    public void saveChatFromMessage(Message message,
                                    com.statistictgchatbot.model.submodel.Message messageToDb) throws JsonProcessingException {
        Chat chat = chatConverter.createChat(message, messageToDb);
        chatService.saveChat(chat);
    }
}
