package com.statistictgchatbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistictgchatbot.service.ChatByRealTimeService;
import com.statistictgchatbot.service.UpdateReceivedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;


@Component
public class BotController implements LongPollingUpdateConsumer {

    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private final UpdateReceivedService updateReceivedService;
    private final ChatByRealTimeService chatByRealTimeService;

    public BotController(UpdateReceivedService updateReceivedService, ChatByRealTimeService chatByRealTimeService) {
        this.updateReceivedService = updateReceivedService;
        this.chatByRealTimeService = chatByRealTimeService;
    }

    @Override
    public void consume(List<Update> list) {
        for(Update update : list) {
            if (update.hasMessage()) {
                Long chatId = update.getMessage().getChatId();
                Message message = update.getMessage();

                if (message.hasText() && message.getText().startsWith("/")) {
                    String messageText = update.getMessage().getText();
                    updateReceivedService.updateReceivedMessageByCommand(messageText, chatId);
                }

                if(message.hasText()) {
                    try {
                        chatByRealTimeService.getOrSaveChat(String.valueOf(chatId), message);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (update.getMessage().hasDocument()) {
                    Document document = update.getMessage().getDocument();
                    updateReceivedService.updateReceivedDocument(document, chatId);
                }
            }
        }
    }
}
