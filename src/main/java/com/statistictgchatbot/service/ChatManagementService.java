package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface ChatManagementService {

    void saveChatFromMessage(Message message,
                             com.statistictgchatbot.model.submodel.Message messageToDb) throws JsonProcessingException;
}
