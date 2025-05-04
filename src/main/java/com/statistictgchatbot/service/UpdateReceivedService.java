package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.api.objects.Document;

public interface UpdateReceivedService {

    void updateReceivedMessage(String messageText, Long chatId);

    void updateReceivedDocument(Document document, Long chatId);
}
