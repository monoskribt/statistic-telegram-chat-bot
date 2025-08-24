package com.statistictgchatbot.controller;

import com.statistictgchatbot.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BotController implements LongPollingUpdateConsumer {
    private final BotService botService;

    @Override
    public void consume(List<Update> list) {
        for (Update update : list) {
            System.out.println("RAW UPDATE: " + update.toString());

            if (update.getMessageReaction() != null) {
                System.out.println("REACTION UPDATE CATCHED: " + update.getMessageReaction());
            }

            botService.handleBotEvents(update);
        }
    }

}
