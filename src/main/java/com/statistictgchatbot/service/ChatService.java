package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.model.Chat;

import java.util.List;

public interface ChatService {
    void saveChat(Chat chatToSave);

    void deleteChat(Chat chatToDelete);

    Chat getChatByChatId(String chatId);

    boolean chatIsExist(String chatId);

    String createHashIdForChat(Chat chat, ObjectMapper objectMapper) throws JsonProcessingException;

    List<Chat> getAllChats();
}
