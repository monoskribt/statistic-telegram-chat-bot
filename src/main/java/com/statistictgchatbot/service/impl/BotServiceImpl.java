package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.MessageToUser;
import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
public class BotServiceImpl implements BotService {

    private final ChatService chatService;
    private final FileService fileService;
    private final ChatStatsService chatStatsService;
    private final MessageSender messageSender;

    public BotServiceImpl(@Lazy ChatService chatService,
                          @Lazy FileService fileService,
                          @Lazy MessageSender messageSender,
                          @Lazy ChatStatsService chatStatsService) {
        this.chatService = chatService;
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
            Chat chat = chatService
                    .parseChatFromFile("src/main/resources/uploaded/" + fileName);
            chatService.saveChatEntity(chat, chatId);
            fileService.deleteFileFromLocal(fileName);
        } catch (IOException | TelegramApiException e) {
            messageSender.sendMessage(chatId, "Failed while parsing file. " +
                    "Check your file and try again later");
            throw new FileDownloadException("Failed to download file");
        }
    }

    @Override
    public void getUserActivity(Long chatId, String chatName, String messageText) throws TelegramApiException {
        chatStatsService.getUserActivity(chatId, chatName, messageText);
    }

    @Override
    public void getInactiveUsersForAWeek(Long chatId, String chatName) throws TelegramApiException {
        chatStatsService.getUsersWithoutActivityMoreThanWeek(chatId, chatName);
    }

    @Override
    public void getAverageMessagePerDay(Long chatId, String chatName) throws TelegramApiException {
        chatStatsService.getAverageMessagesPerDayByLastMonth(chatId, chatName);
    }

    @Override
    public void getChatReport(Long chatId, String chatName) throws TelegramApiException, IOException {
        chatStatsService.prepareStatisticCountOfMessageToGraph(chatId, chatName);
    }


    @Override
    public void sendDefaultMessage(Long chatId) throws TelegramApiException {
        messageSender.sendMessage(chatId, MessageToUser.UNKNOWN_COMMAND);
    }
}
