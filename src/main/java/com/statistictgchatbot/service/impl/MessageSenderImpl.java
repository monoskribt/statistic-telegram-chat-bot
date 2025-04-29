package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.service.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Service
public class MessageSenderImpl implements MessageSender {

    private final AbsSender absSender;

    public MessageSenderImpl(AbsSender absSender) {
        this.absSender = absSender;
    }

    @Override
    public void sendMessage(Long id, String text) throws TelegramApiException {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(id)
                .parseMode("Markdown")
                .text(text)
                .build();
        absSender.execute(sendMessage);
    }

    @Override
    public void sendPhoto(Long chatId, String pathName) throws TelegramApiException {
        SendPhoto sendPhoto = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(new File(pathName)))
                .build();
        absSender.execute(sendPhoto);
    }
}
