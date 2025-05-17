package com.statistictgchatbot.controller;

import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.BotService;
import com.statistictgchatbot.service.UpdateReceivedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@Component
public class BotController implements LongPollingUpdateConsumer {

    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private final UpdateReceivedService updateReceivedService;

    public BotController(UpdateReceivedService updateReceivedService) {
        this.updateReceivedService = updateReceivedService;
    }

    @Override
    public void consume(List<Update> list) {
        for(Update update : list) {
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
}
