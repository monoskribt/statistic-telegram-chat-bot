package com.statistictgchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistictgchatbot.model.UserEvent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public interface UserEventService {

    UserEvent parseUserEventFromFile(String filePath) throws IOException;

    void saveUserEventEntity(UserEvent userEvent, Long chatId)
            throws JsonProcessingException, TelegramApiException;

}
