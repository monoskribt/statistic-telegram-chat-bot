package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistictgchatbot.model.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ChatManagementService {

    Chat parseChatFromFile(String filePath);

    void saveChatFromFile(Chat chat, Long chatId) throws JsonProcessingException, TelegramApiException;

    void createOrUpdateChatFromMessage(String chatId, Message message) throws JsonProcessingException;
}
