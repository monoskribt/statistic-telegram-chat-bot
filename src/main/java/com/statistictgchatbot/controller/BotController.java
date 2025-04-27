package com.statistictgchatbot.controller;

import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.BotService;
import com.statistictgchatbot.util.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Objects;

@Component
public class BotController extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private final BotProps botProps;
    private final BotService botService;

    public BotController(BotProps botProps,
                         BotService botService) {
        super(botProps.token());
        this.botProps = botProps;
        this.botService = botService;
    }

    @Override
    public String getBotUsername() {
        return botProps.name();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                String[] splitMessage = messageText.split("\\s+");
                String chatName = splitMessage[splitMessage.length - 1];

                String command = CommandUtils.getCommandFromMessage(messageText);

                try {
                    switch (Objects.requireNonNull(command)) {
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

            if (update.getMessage().hasDocument()) {
                Document document = update.getMessage().getDocument();
                String fieldId = document.getFileId();
                String fileName = document.getFileName();

                try {
                    botService.documentProcessing(fileName, fieldId, chatId);
                } catch (TelegramApiException e) {
                    throw new FileDownloadException("Failed while download file");
                }
            }
        }
    }
}
