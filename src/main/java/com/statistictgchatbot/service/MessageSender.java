package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface MessageSender {

    void sendMessage(Long id, String text) throws TelegramApiException;

    void sendPhoto(Long chatId, SendPhoto sendPhoto) throws TelegramApiException;
}
