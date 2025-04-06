package com.statistictgchatbot.controller;

import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.FileService;
import com.statistictgchatbot.service.UserEventService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
public class BotController extends TelegramLongPollingBot {

    private final BotProps botProps;
    private final UserEventService userEventService;
    private final FileService fileService;

    public BotController(BotProps botProps, UserEventService userEventService, FileService fileService) {
        super(botProps.token());
        this.botProps = botProps;
        this.userEventService = userEventService;
        this.fileService = fileService;
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

            try {
                fileService.uploadFile(fileName, fieldId);
                userEventService.parseUserEventFromFile("src/main/resources/uploaded/" + fileName);
            } catch (IOException e) {
                sendMessage(chatId, "Failed to download file. Try again later!");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
                throw new FileDownloadException("Failed to download file");
            }

        }
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .parseMode("Markdown")
                .text(message)
                .build();
    }
}
