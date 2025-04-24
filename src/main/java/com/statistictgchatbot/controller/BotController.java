package com.statistictgchatbot.controller;

import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.BotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
                String[] arrMessages = messageText.split("\\s+");
                String chatName = arrMessages[arrMessages.length - 1];

                if (messageText.startsWith(BotCommands.MOST_ACTIVE_USERS) ||
                    messageText.startsWith(BotCommands.MOST_INACTIVE_USERS)) {
                    try {
                        botService.getUserActivity(chatId, chatName, messageText);
                    } catch (TelegramApiException e) {
                        log.warn("Problem with method getStats or chat name");
                    }
                    return;
                }
                if(messageText.startsWith(BotCommands.INACTIVE_BY_WEEK)) {
                    try {
                        botService.getInactiveUserByWeek(chatId, chatName);
                    } catch (TelegramApiException e) {
                        log.warn("Problem with method getInactiveUserByWeek or chat name");
                    }
                }
                if(messageText.startsWith(BotCommands.AVERAGE_MESSAGE_PER_DAY)) {
                    try {
                        botService.getAverageMessagePerDay(chatId, chatName);
                    } catch (TelegramApiException e) {
                        log.warn("Problem with method getAverageMessagePerDay or chat name");
                    }
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
