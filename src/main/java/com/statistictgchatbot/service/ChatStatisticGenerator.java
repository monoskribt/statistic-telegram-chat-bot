package com.statistictgchatbot.service;

import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.dto.UserMessageStatsDTO;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ChatStatisticGenerator {

    void getUserActivity(Long chatId,
                         String chatName,
                         String messageText,
                         boolean filterByLastWeek) throws TelegramApiException;

    List<UserMessageStatsDTO> activeStatusUsers(String chatName,
                                                String activeStatus,
                                                boolean filterByLastWeek);

    void getUsersWithoutActivityMoreThanWeek(
            Long chatId,
            String chatName) throws TelegramApiException;

    void getAverageMessagesPerDayByLastMonth(Long chatId,
                                 String chatName) throws TelegramApiException;

    void prepareStatisticCountOfMessageToGraph(Long chatId,
                                               String chatName) throws TelegramApiException, IOException;

    Map<TypeOfEvent, Integer> getJoinedAndLeftUsersCount(String chatName);
}
