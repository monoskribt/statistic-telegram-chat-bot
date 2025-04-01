package com.statistictgchatbot.controller;

import com.statistictgchatbot.props.BotProps;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotController extends TelegramLongPollingBot {

    private final BotProps botProps;

    public BotController(BotProps botProps) {
        super(botProps.token());
        this.botProps = botProps;
    }

    @Override
    public String getBotUsername() {
        return botProps.name();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
