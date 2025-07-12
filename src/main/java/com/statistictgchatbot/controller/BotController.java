package com.statistictgchatbot.controller;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
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

    public BotController(UpdateReceivedService updateReceivedService) {
        this.updateReceivedService = updateReceivedService;
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

                extracted(message, chatId);

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

    private void extracted(Message message, Long chatId) {
        if(message.hasText()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.TEXT);
        }

        if(message.hasAudio()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.AUDIO);
        }

        if(message.hasVoice()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.VOICE_MESSAGE);
        }

        if(message.hasVideo()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.VIDEO_FILE);
        }

        if(message.hasVideoNote()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.VIDEO_MESSAGE);
        }

        if(message.hasPhoto()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.PHOTO);
        }

        if(message.hasAnimation()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.ANIMATION);
        }

        if(message.hasSticker()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.STICKER);
        }
    }
}
