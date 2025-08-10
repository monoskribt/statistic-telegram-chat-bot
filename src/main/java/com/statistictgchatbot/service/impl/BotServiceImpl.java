package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotServiceImpl implements BotService {
    private final UpdateReceivedService updateReceivedService;
    private final MessageService messageService;
    private final ChatService chatService;

    @Override
    public void handleBotEvents(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            Message message = update.getMessage();

            handleCommand(update, message, chatId);
            handleMessage(message, chatId);
            handleDocument(update, chatId);
        }
        if(update.hasEditedMessage()) {
            handleEditedMessage(update);
        }
    }

    @Override
    public void handleEditedMessage(Update update) {
        Message editedMessage = update.getEditedMessage();
        chatService.chatIsExist(String.valueOf(editedMessage.getChatId()));
        if(chatService.chatIsExist(String.valueOf(editedMessage.getChatId()))) {
            try {
                com.statistictgchatbot.model.submodel.Message message = messageService
                        .getMessageByChatAndMessageId(editedMessage.getChatId(), editedMessage.getMessageId());
                createEditedMessage(message, editedMessage);
                messageService.updateMessage(String.valueOf(editedMessage.getChatId()), editedMessage.getMessageId(), message);
            } catch (Exception e) {
                log.warn("Exception: {}", e.getMessage());
            }
        }
    }

    private static void createEditedMessage(com.statistictgchatbot.model.submodel.Message message, Message editedMessage) {
        message.setCaption(editedMessage.getCaption());
        message.setText(editedMessage.getText());
        message.setEditedAt(editedMessage.getEditDate());
    }

    private void handleCommand(Update update, Message message, Long chatId) {
        if (message.hasText() && message.getText().startsWith("/")) {
            String messageText = update.getMessage().getText();
            updateReceivedService.updateReceivedMessageByCommand(messageText, chatId);
        }
    }

    private void handleMessage(Message message, Long chatId) {
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

    private void handleDocument(Update update, Long chatId) {
        if (update.getMessage().hasDocument()) {
            org.telegram.telegrambots.meta.api.objects.chat.Chat tgChat = update.getMessage().getChat();

            if (tgChat.isUserChat()) {
                Document document = update.getMessage().getDocument();
                updateReceivedService.updateReceivedDocument(document, chatId);
            }
        }
    }
}
