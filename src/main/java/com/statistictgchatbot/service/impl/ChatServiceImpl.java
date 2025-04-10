package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.constant.MessageToUser;
import com.statistictgchatbot.exception.ChatNotFoundException;
import com.statistictgchatbot.exception.ParseFileException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.repository.ChatRepo;
import com.statistictgchatbot.service.MessageSender;
import com.statistictgchatbot.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepo chatRepo;
    private final MessageSender messageSender;

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    public ChatServiceImpl(ChatRepo chatRepo, MessageSender messageSender) {
        this.chatRepo = chatRepo;
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
        String hashId = DigestUtils.md5DigestAsHex(objectMapper.writeValueAsBytes(chat));
        chat.setId(hashId);

        if(chatRepo.existsById(hashId)) {
            messageSender.sendMessage(chatId, MessageToUser.ALREADY_EXISTS);
            log.info("Chat history is already present");
            return;
        }
        compareChatsBySizeOfMessages(chat, chatId);
    }

    private void compareChatsBySizeOfMessages(Chat chat, Long chatId) throws TelegramApiException {
        try {
            Chat existingChat = getChatByName(chat.getChatName());
            int existingSizeOfMessages = existingChat.getMessages().size();
            int newSizeOfMessages = chat.getMessages().size();

            log.info("Count of existing messages: {} from the chat: {}",
                    existingSizeOfMessages, existingChat.getChatName());
            log.info("Count of new messages: {} from the chat: {}",
                    newSizeOfMessages, chat.getChatName());

            if (existingSizeOfMessages < newSizeOfMessages) {
                chatRepo.delete(existingChat);
                chatRepo.save(chat);
                messageSender.sendMessage(chatId, MessageToUser.SUCCESSFULLY_UPDATED);
                log.info("Chat was successfully updated with new messages");
            } else {
                messageSender.sendMessage(chatId, MessageToUser.CHAT_WAS_NOT_SAVED);
            }
        } catch (ChatNotFoundException e) {
            chatRepo.save(chat);
            messageSender.sendMessage(chatId, MessageToUser.SUCCESSFULLY_SAVED);
            log.info("Chat history saved");
        }
    }


    @Override
    public Chat getChatByName(String chatName) {
        List<Chat> allChats = chatRepo.findAll();

        return allChats
                .stream()
                .filter(chatWithName -> chatWithName.getChatName().equals(chatName))
                .findFirst()
                .orElseThrow(() -> new ChatNotFoundException("Chat with name " + chatName +
                        "is not present"));
    }
}
