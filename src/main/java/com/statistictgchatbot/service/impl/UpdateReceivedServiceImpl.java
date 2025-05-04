package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.service.BotService;
import com.statistictgchatbot.service.UpdateReceivedService;
import com.statistictgchatbot.util.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
public class UpdateReceivedServiceImpl implements UpdateReceivedService {

    private final BotService botService;

    private final static Logger log = LoggerFactory.getLogger(UpdateReceivedServiceImpl.class);

    public UpdateReceivedServiceImpl(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void updateReceivedMessage(String messageText, Long chatId) {
        String[] splitMessage = messageText.split("\\s+");
        String chatName = splitMessage[splitMessage.length - 1];

        String command = CommandUtils.getCommandFromMessage(messageText);

        try {
            switch (command) {
                case BotCommands.MOST_ACTIVE_USERS, BotCommands.MOST_INACTIVE_USERS ->
                        botService.getUserActivity(chatId, chatName, messageText);
                case BotCommands.INACTIVE_BY_WEEK ->
                        botService.getInactiveUsersForAWeek(chatId, chatName);
                case BotCommands.AVERAGE_MESSAGE_PER_DAY ->
                        botService.getAverageMessagePerDay(chatId, chatName);
                case BotCommands.CHAT_REPORT ->
                        botService.getChatReport(chatId, chatName);
                case BotCommands.UNKNOWN_COMMAND ->
                        botService.sendDefaultMessage(chatId);
            }
        } catch (TelegramApiException | IOException e) {
            log.warn("Problem with chat name or method");
        }
    }

    @Override
    public void updateReceivedDocument(Document document, Long chatId) {
        String fieldId = document.getFileId();
        String fileName = document.getFileName();

        try {
            botService.documentProcessing(fileName, fieldId, chatId);
        } catch (TelegramApiException e) {
            throw new FileDownloadException("Failed during download file");
        }
    }
}
