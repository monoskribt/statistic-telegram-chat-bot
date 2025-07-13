package com.statistictgchatbot.controller;

import com.statistictgchatbot.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BotController implements LongPollingUpdateConsumer {
    private final BotService botService;

    @SneakyThrows
    @Override
    public void consume(List<Update> list) {
        for(Update update : list) {
            if (update.hasMessage()) {
                botService.handleBotEvents(update);
            }
        }
    }
}
