package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.exception.ChatNotFoundException;
import com.statistictgchatbot.exception.ParseFileException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.ChatService;
import com.statistictgchatbot.service.MessageSender;
import com.statistictgchatbot.service.ChatByDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

import static com.statistictgchatbot.constant.MessageToUser.*;

@Service
public class ChatByDocumentServiceImpl implements ChatByDocumentService {

    private final ChatService chatService;
    private final MessageSender messageSender;

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    public ChatByDocumentServiceImpl(ChatService chatService, MessageSender messageSender) {
        this.chatService = chatService;
        this.messageSender = messageSender;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Chat parseChatFromFile(String filePath) {
        log.info("Start parse from file path: {}", filePath);
        try {
            return objectMapper.readValue(new File(filePath), Chat.class);
        } catch (IOException e) {
            throw new ParseFileException("Failed while parsing", e.getCause());
        }
    }

    @Override
    public void saveChatEntity(Chat chat, Long chatId)
            throws JsonProcessingException, TelegramApiException {
        String hashId = chatService.createIdHashIdForChat(chat, objectMapper);
        chat.setId(hashId);

        if(chatService.chatIsExist(hashId)) {
            messageSender.sendMessage(chatId, ALREADY_EXISTS);
            log.info("Chat history is already present");
            return;
        }
        compareChatsBySizeOfMessages(chat, chatId);
    }

    private void compareChatsBySizeOfMessages(Chat chat, Long chatId) throws TelegramApiException {
        try {
            Chat existingChat = chatService.getChatByChatId(chat.getChatId());
            int existingSizeOfMessages = existingChat.getMessages().size();
            int newSizeOfMessages = chat.getMessages().size();

            log.info("Count of existing messages: {} from the chat: {}",
                    existingSizeOfMessages, existingChat.getChatName());
            log.info("Count of new messages: {} from the chat: {}",
                    newSizeOfMessages, chat.getChatName());

            if (existingSizeOfMessages < newSizeOfMessages) {
                chatService.deleteChat(existingChat);
                chatService.saveChat(chat);
                messageSender.sendMessage(chatId, SUCCESSFULLY_UPDATED);
                log.info("Chat was successfully updated with new messages");
            } else {
                messageSender.sendMessage(chatId, CHAT_WAS_NOT_SAVED);
            }
        } catch (ChatNotFoundException e) {
            chatService.saveChat(chat);
            messageSender.sendMessage(chatId, SUCCESSFULLY_SAVED);
            log.info("Chat history saved");
        }
    }
}
