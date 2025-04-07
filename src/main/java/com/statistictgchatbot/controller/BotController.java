package com.statistictgchatbot.controller;

import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.BotService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotController extends TelegramLongPollingBot {

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
        if(update.hasMessage() && update.getMessage().hasDocument()) {
            Document document = update.getMessage().getDocument();

            String fieldId = document.getFileId();
            String fileName = document.getFileName();
            Long chatId = update.getMessage().getChatId();

            botService.documentProcessing(fileName, fieldId, chatId);
        }
    }
}
