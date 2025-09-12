package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ChatStatisticGeneratorScheduler {

    void generateChatActivityStatistic(Long chatId, String chatName, String activityStatus, boolean filterByLastWeek) throws TelegramApiException;
}
