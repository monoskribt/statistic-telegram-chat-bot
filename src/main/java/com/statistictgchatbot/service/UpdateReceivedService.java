package com.statistictgchatbot.service;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface UpdateReceivedService {

    void updateReceivedMessageByCommand(String messageText, Long chatId);

    void updateReceivedMessage(String chatId, Message message, TypeOfEvent typeOfEvent, MediaType mediaType);

    void updateReceivedDocument(Document document, Long chatId);
}
