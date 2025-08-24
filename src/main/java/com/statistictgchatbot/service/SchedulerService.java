package com.statistictgchatbot.service;

import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.repository.ChatRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    private final ChatService chatService;
    private final ChatRepo chatRepo;
    private final ChatStatisticGenerator chatStatisticGenerator;

    @Scheduled(cron = "0 25 18 * * *")
    public void generateMostActiveUsersByWeek() {
        List<Chat> chats = chatService.getAllChats();
        log.info(chats.toString());

        if(!chats.isEmpty()) {
            chats.forEach(ch -> {
                try {
                    chatStatisticGenerator.getUserActivity(
                            Long.valueOf(ch.getChatId()),
                            ch.getChatName(),
                            BotCommands.MOST_ACTIVE_USERS + ch.getChatName(),
                            true);
                } catch (TelegramApiException exception) {
                    log.error("Problem during generating statistic scheduler: {}. Method name: generateStatisticsForChats",
                            exception.getMessage());
                }
            });
        }
    }
}
