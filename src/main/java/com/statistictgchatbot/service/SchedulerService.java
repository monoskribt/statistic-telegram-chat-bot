package com.statistictgchatbot.service;

import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.model.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    private final ChatService chatService;
    private final ChatStatisticGenerator chatStatisticGenerator;

    @Scheduled(cron = "0 27 21 * * *")
    public void generateMostActiveUsersByWeek() {
        List<Chat> chats = chatService.getAllChats();

        if(!chats.isEmpty()) {
            chats.forEach(ch -> {
                try {
                    chatStatisticGenerator.getUserActivity(
                            Long.valueOf(ch.getChatId()),
                            ch.getChatName(),
                            BotCommands.MOST_ACTIVE_USERS,
                            true);
                    log.info("Created statistic for the chat: {}", ch.getChatName());
                } catch (Exception e) {
                    log.error("Error during working method generateMostActiveUsersByWeek: {}", e.getMessage());
                }
            });
        }
    }
}
