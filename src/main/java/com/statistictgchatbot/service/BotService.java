package com.statistictgchatbot.service;


import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public interface BotService {

    void documentProcessing(String fileName,
                                   String fieldId,
                                   Long chatId) throws TelegramApiException;

    void getUserActivity(Long chatId, String chatName, String messageText)
            throws TelegramApiException;

    void getInactiveUsersForAWeek(Long chatId, String chatName)
            throws TelegramApiException;

    void getAverageMessagePerDay(Long chatId, String chatName)
            throws TelegramApiException;

    void getChatReport(Long chatId, String chatName)
            throws TelegramApiException, IOException;

    void sendDefaultMessage(Long chatId) throws TelegramApiException;
}
