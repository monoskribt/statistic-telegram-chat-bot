package com.statistictgchatbot.service;


import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface BotService {

    void documentProcessing(String fileName,
                                   String fieldId,
                                   Long chatId) throws TelegramApiException;

    void getStats(Long chatId, String chatName) throws TelegramApiException;
}
