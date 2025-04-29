package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistictgchatbot.model.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public interface ChatService {

    Chat parseChatFromFile(String filePath) throws IOException;

    void saveChatEntity(Chat userEvent, Long chatId)
            throws JsonProcessingException, TelegramApiException;

    Chat getChatByName(String chatName);

    boolean chatIsExist(String chatName);

}
