package com.statistictgchatbot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {

    void handleBotEvents(Update update);
}
