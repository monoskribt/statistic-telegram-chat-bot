package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.model.UserEvent;
import com.statistictgchatbot.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
public class BotServiceImpl implements BotService {

    private final UserEventService userEventService;
    private final FileService fileService;
    private final ChatStatsService chatStatsService;
    private final MessageSender messageSender;

    public BotServiceImpl(@Lazy UserEventService userEventService,
                          @Lazy FileService fileService,
                          @Lazy MessageSender messageSender,
                          @Lazy ChatStatsService chatStatsService) {
        this.userEventService = userEventService;
        this.fileService = fileService;
        this.chatStatsService = chatStatsService;
        this.messageSender = messageSender;
    }

    @Override
    public void documentProcessing(String fileName,
                                   String fieldId,
                                   Long chatId) throws TelegramApiException {
        try {
            fileService.uploadFile(fileName, fieldId);
            UserEvent userEvent = userEventService
                    .parseUserEventFromFile("src/main/resources/uploaded/" + fileName);
            userEventService.saveUserEventEntity(userEvent, chatId);
            fileService.deleteFileFromLocal(fileName);
        } catch (IOException | TelegramApiException e) {
            messageSender.sendMessage(chatId, "Failed while parsing file. " +
                    "Check your file and try again later");
            throw new FileDownloadException("Failed to download file");
        }
    }

    @Override
    public void getStats(Long chatId, String chatName) throws TelegramApiException {
        chatStatsService.getMostActiveUsers(chatId, chatName);
    }
}
