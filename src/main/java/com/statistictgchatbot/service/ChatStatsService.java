package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ChatStatsService {

    void getUserActivity(Long chatId, String chatName, String messageText)
            throws TelegramApiException;

    void getUsersWithoutActivityMoreThanWeek(
            Long chatId,
            String chatName) throws TelegramApiException;
}
