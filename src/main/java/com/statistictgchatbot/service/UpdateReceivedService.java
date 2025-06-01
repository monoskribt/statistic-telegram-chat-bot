package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface UpdateReceivedService {

    void updateReceivedMessageByCommand(String messageText, Long chatId);

    void updateReceivedMessage(String messageText, Long chatId, Message message);

    void updateReceivedDocument(Document document, Long chatId);
}
