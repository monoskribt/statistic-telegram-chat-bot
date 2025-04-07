package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.model.UserEvent;
import com.statistictgchatbot.service.BotService;
import com.statistictgchatbot.service.FileService;
import com.statistictgchatbot.service.UserEventService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
public class BotServiceImpl implements BotService {

    private final UserEventService userEventService;
    private final FileService fileService;

    public BotServiceImpl(@Lazy UserEventService userEventService,
                          @Lazy FileService fileService) {
        this.userEventService = userEventService;
        this.fileService = fileService;
    }

    @Override
    public void documentProcessing(String fileName,
                                   String fieldId,
                                   Long chatId) {
        try {
            fileService.uploadFile(fileName, fieldId);
            UserEvent userEvent = userEventService
                    .parseUserEventFromFile("src/main/resources/uploaded/" + fileName);
            userEventService.saveUserEventEntity(userEvent, chatId);
            fileService.deleteFileFromLocal(fileName);
        } catch (IOException | TelegramApiException e) {
            throw new FileDownloadException("Failed to download file");
        }
    }
}
