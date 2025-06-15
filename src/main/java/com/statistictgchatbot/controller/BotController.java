package com.statistictgchatbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistictgchatbot.service.ChatByRealTimeService;
import com.statistictgchatbot.service.UpdateReceivedService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;


@Component
public class BotController implements LongPollingUpdateConsumer {
    private final UpdateReceivedService updateReceivedService;
    private final ChatByRealTimeService chatByRealTimeService;

    public BotController(UpdateReceivedService updateReceivedService, ChatByRealTimeService chatByRealTimeService) {
        this.updateReceivedService = updateReceivedService;
        this.chatByRealTimeService = chatByRealTimeService;
    }

    @SneakyThrows
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
                    updateReceivedService.updateReceivedMessage(String.valueOf(chatId), message);
                }

                if (update.getMessage().hasDocument()) {
                    Chat tgChat = update.getMessage().getChat();

                    if (tgChat.isUserChat()) {
                        Document document = update.getMessage().getDocument();
                        updateReceivedService.updateReceivedDocument(document, chatId);
                    }
                }
            }
        }
    }
}
