package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.model.Chat;

public interface ChatService {
    void saveChat(Chat chatToSave);

    void deleteChat(Chat chatToDelete);

    Chat getChatByChatId(String chatId);

    boolean chatIsExist(String chatId);

    String createIdHashIdForChat(Chat chat, ObjectMapper objectMapper) throws JsonProcessingException;
}
