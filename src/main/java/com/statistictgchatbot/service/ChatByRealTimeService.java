package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface ChatByRealTimeService {

    void createOrUpdateChat(String chatId, Message message) throws JsonProcessingException;
}
