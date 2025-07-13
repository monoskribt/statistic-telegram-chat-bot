package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.annotation.CheckChatExists;
import com.statistictgchatbot.constant.MessageToUser;
import com.statistictgchatbot.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
public class ChatAnalyticsService implements ChatAnalytics {

    private final ChatStatisticGenerator chatStatisticGenerator;
    private final MessageSender messageSender;

    public ChatAnalyticsService(@Lazy MessageSender messageSender,
                                @Lazy ChatStatisticGenerator chatStatisticGenerator) {
        this.chatStatisticGenerator = chatStatisticGenerator;
        this.messageSender = messageSender;
    }

    @CheckChatExists
    @Override
    public void getUserActivity(Long chatId, String chatName, String messageText) throws TelegramApiException {
        chatStatisticGenerator.getUserActivity(chatId, chatName, messageText);
    }

    @CheckChatExists
    @Override
    public void getInactiveUsersForAWeek(Long chatId, String chatName) throws TelegramApiException {
        chatStatisticGenerator.getUsersWithoutActivityMoreThanWeek(chatId, chatName);
    }

    @CheckChatExists
    @Override
    public void getAverageMessagePerDay(Long chatId, String chatName) throws TelegramApiException {
        chatStatisticGenerator.getAverageMessagesPerDayByLastMonth(chatId, chatName);
    }

    @CheckChatExists
    @Override
    public void getChatReport(Long chatId, String chatName) throws TelegramApiException, IOException {
        chatStatisticGenerator.prepareStatisticCountOfMessageToGraph(chatId, chatName);
    }


    @Override
    public void sendDefaultMessage(Long chatId) throws TelegramApiException {
        messageSender.sendMessage(chatId, MessageToUser.UNKNOWN_COMMAND);
    }
}
