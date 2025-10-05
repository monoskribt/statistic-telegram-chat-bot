package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.dto.UserMessageStatsDTO;
import com.statistictgchatbot.service.ChatStatisticGenerator;
import com.statistictgchatbot.service.ChatStatisticGeneratorScheduler;
import com.statistictgchatbot.service.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;

import static com.statistictgchatbot.util.FormattingMessage.formatMostActiveUsersMessageWithUsersFlow;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatStatisticGeneratorSchedulerImpl implements ChatStatisticGeneratorScheduler {

    private final ChatStatisticGenerator chatStatisticGenerator;
    private final MessageSender messageSender;

    @Override
    public void generateChatActivityStatistic(
            Long chatId,
            String chatName,
            String activityStatus,
            boolean filterByLastWeek) throws TelegramApiException {
        List<UserMessageStatsDTO> statsActivityOfUsers = chatStatisticGenerator.activeStatusUsers(
                chatName, activityStatus, filterByLastWeek)
                .stream()
                .filter(stat -> stat.getUsername() != null)
                .toList();
        Map<TypeOfEvent, Integer> getJoinedAndLeftStatsOfChat = chatStatisticGenerator
                .getJoinedAndLeftUsersCount(chatName);
        if(!statsActivityOfUsers.isEmpty()) {
            String result = formatMostActiveUsersMessageWithUsersFlow(statsActivityOfUsers, getJoinedAndLeftStatsOfChat);
            messageSender.sendMessage(chatId, result);
        }
    }
}
