package com.statistictgchatbot.service;


public interface BotService {

    void documentProcessing(String fileName,
                                   String fieldId,
                                   Long chatId);
}
