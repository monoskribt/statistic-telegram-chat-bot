package com.statistictgchatbot.service;

import com.statistictgchatbot.model.submodel.Message;

public interface MessageService {
    void appendMessage(String chatId, Message message);

    void updateMessage(String chatId, int messageId, Message updatedMessage);

    Message getMessageByChatAndMessageId(Long chatId, int messageId);
}
