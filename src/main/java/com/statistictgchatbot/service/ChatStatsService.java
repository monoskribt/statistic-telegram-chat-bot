package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public interface ChatStatsService {

    void getUserActivity(Long chatId,
                         String chatName,
                         String messageText) throws TelegramApiException;

    void getUsersWithoutActivityMoreThanWeek(
            Long chatId,
            String chatName) throws TelegramApiException;

    void getAverageMessagesPerDayByLastMonth(Long chatId,
                                 String chatName) throws TelegramApiException;

    void prepareStatisticCountOfMessageToGraph(Long chatId,
                                               String chatName) throws TelegramApiException, IOException;
}
