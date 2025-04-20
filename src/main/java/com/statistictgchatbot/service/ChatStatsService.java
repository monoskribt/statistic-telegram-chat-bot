package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ChatStatsService {

    void getMostActiveUsers(Long chatId, String chatName) throws TelegramApiException;
}
