package com.statistictgchatbot.controller;

import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.BotService;
import com.statistictgchatbot.service.UpdateReceivedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class BotController extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private final BotProps botProps;
    private final UpdateReceivedService updateReceivedService;

    public BotController(BotProps botProps,
                         BotService botService,
                         UpdateReceivedService updateReceivedService) {
        super(botProps.token());
        this.botProps = botProps;
        this.updateReceivedService = updateReceivedService;
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
                updateReceivedService.updateReceivedMessage(messageText, chatId);
            }

            if (update.getMessage().hasDocument()) {
                Document document = update.getMessage().getDocument();
                updateReceivedService.updateReceivedDocument(document, chatId);
            }
        }
    }
}
