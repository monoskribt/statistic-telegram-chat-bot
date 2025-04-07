package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.constant.MessageToUser;
import com.statistictgchatbot.model.UserEvent;
import com.statistictgchatbot.repository.UserEventRepo;
import com.statistictgchatbot.service.MessageSender;
import com.statistictgchatbot.service.UserEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

@Service
public class UserEventServiceImpl implements UserEventService {

    private final UserEventRepo userEventRepo;
    private final MessageSender messageSender;

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    public UserEventServiceImpl(UserEventRepo userEventRepo, MessageSender messageSender) {
        this.userEventRepo = userEventRepo;
        this.messageSender = messageSender;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserEvent parseUserEventFromFile(String filePath) throws IOException {
        log.info("Start parse from file path: {}", filePath);
        return objectMapper.readValue(new File(filePath), UserEvent.class);
    }

    @Override
    public void saveUserEventEntity(UserEvent userEvent, Long chatId) throws JsonProcessingException, TelegramApiException {
        String hashId = DigestUtils.md5DigestAsHex(objectMapper.writeValueAsBytes(userEvent));
        userEvent.setId(hashId);

        if(!userEventRepo.existsById(hashId)) {
            userEventRepo.save(userEvent);
            messageSender.sendMessage(chatId, MessageToUser.SUCCESSFULLY_SAVED);
            log.info("User Event saved");
        }
        else {
            messageSender.sendMessage(chatId, MessageToUser.ALREADY_EXISTS);
            log.info("User Event is already present");
        }
    }
}
