package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.annotation.CheckChatExists;
import com.statistictgchatbot.constant.MessageToUser;
import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static com.statistictgchatbot.constant.Constants.PATH_TO_UPLOADED_FILE;

@Service
public class BotServiceImpl implements BotService {

    private final ChatManagementService chatManagementService;
    private final FileService fileService;
    private final ChatStatisticGenerator chatStatisticGenerator;
    private final MessageSender messageSender;

    public BotServiceImpl(@Lazy ChatManagementService chatManagementService,
                          @Lazy FileService fileService,
                          @Lazy MessageSender messageSender,
                          @Lazy ChatStatisticGenerator chatStatisticGenerator) {
        this.chatManagementService = chatManagementService;
        this.fileService = fileService;
        this.chatStatisticGenerator = chatStatisticGenerator;
        this.messageSender = messageSender;
    }

    @Override
    public void documentProcessing(String fileName,
                                   String fieldId,
                                   Long chatId) throws TelegramApiException {
        try {
            fileService.uploadFile(fileName, fieldId);
            Chat chat = chatManagementService
                    .parseChatFromFile(PATH_TO_UPLOADED_FILE + fileName);
            chatManagementService.saveChatFromFile(chat, chatId);
            fileService.deleteFileFromLocal(fileName);
        } catch (IOException | TelegramApiException e) {
            messageSender.sendMessage(chatId, "Failed while parsing file. " +
                    "Check your file and try again later");
            throw new FileDownloadException("Failed to download file");
        }
    }

    @CheckChatExists
    @Override
    public void getUserActivity(Long chatId, String chatName, String messageText) throws TelegramApiException {
        chatStatisticGenerator.getUserActivity(chatId, chatName, messageText);
    }

    @CheckChatExists
    @Override
    public void getInactiveUsersForAWeek(Long chatId, String chatName) throws TelegramApiException {
        chatStatisticGenerator.getUsersWithoutActivityMoreThanWeek(chatId, chatName);
    }

    @CheckChatExists
    @Override
    public void getAverageMessagePerDay(Long chatId, String chatName) throws TelegramApiException {
        chatStatisticGenerator.getAverageMessagesPerDayByLastMonth(chatId, chatName);
    }

    @CheckChatExists
    @Override
    public void getChatReport(Long chatId, String chatName) throws TelegramApiException, IOException {
        chatStatisticGenerator.prepareStatisticCountOfMessageToGraph(chatId, chatName);
    }


    @Override
    public void sendDefaultMessage(Long chatId) throws TelegramApiException {
        messageSender.sendMessage(chatId, MessageToUser.UNKNOWN_COMMAND);
    }
}
