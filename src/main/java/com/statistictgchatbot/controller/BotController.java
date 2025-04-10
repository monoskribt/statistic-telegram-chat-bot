package com.statistictgchatbot.controller;

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

                if (messageText.startsWith("/stats")) {
                    String chatName = messageText.replace("/stats", "").trim();
                    try {
                        botService.getStats(chatId, chatName);
                    } catch (TelegramApiException e) {
                        log.info("Problem with method getStats. TelegramApiException");
                    }
                    return;
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
