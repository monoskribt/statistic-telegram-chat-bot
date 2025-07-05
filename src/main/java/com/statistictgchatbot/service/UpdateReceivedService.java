package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface UpdateReceivedService {

    void updateReceivedMessageByCommand(String messageText, Long chatId);

    void updateReceivedMessage(String chatId, Message message) throws JsonProcessingException;

    void updateReceivedDocument(Document document, Long chatId);
}
