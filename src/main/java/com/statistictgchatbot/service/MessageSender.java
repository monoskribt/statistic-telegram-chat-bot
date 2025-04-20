package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface MessageSender {

    void sendMessage(Long id, String text) throws TelegramApiException;
}
