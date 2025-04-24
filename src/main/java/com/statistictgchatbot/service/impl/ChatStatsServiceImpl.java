package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.dto.MessageStatsDTO;
import com.statistictgchatbot.dto.UserMessageStatsDTO;
import com.statistictgchatbot.service.ChatStatsService;
import com.statistictgchatbot.service.MessageSender;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ChatStatsServiceImpl implements ChatStatsService {
    private final MessageSender messageSender;
    private final MongoTemplate mongoTemplate;

    public ChatStatsServiceImpl(MessageSender messageSender,
                                MongoTemplate mongoTemplate) {
        this.messageSender = messageSender;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void getUserActivity(Long chatId, String chatName, String activeStatus) throws TelegramApiException {
        List<UserMessageStatsDTO> stats = activeStatusUsers(chatName, activeStatus);

        String result = stats.stream()
                .filter(stat -> stat.getUsername() != null)
                .map(stat -> stat.getUsername() + " -- " + stat.getMessageCount() + " messages")
                .collect(Collectors.joining("\n"));

        messageSender.sendMessage(chatId, result);
    }

    private List<UserMessageStatsDTO> activeStatusUsers(String chatName, String activeStatus) {
        boolean isActive = activeStatus.startsWith(BotCommands.MOST_ACTIVE_USERS);
        boolean isInactive = activeStatus.startsWith(BotCommands.MOST_INACTIVE_USERS);

        if(!isActive && !isInactive) {
            throw new NoSuchElementException("Message is not found");
        }

        MatchOperation matchOperation = Aggregation
                .match(Criteria.where("chatName").is(chatName));

        UnwindOperation unwindMessages = Aggregation.unwind("messages");

        GroupOperation groupByUser = Aggregation
                .group("messages.fromUser")
                .count()
                .as("messageCount");

        SortOperation sortByCount = Aggregation
                .sort(Sort.by(
                        isActive ? Sort.Direction.DESC : Sort.Direction.ASC,
                        "messageCount"));

        LimitOperation limit = Aggregation.limit(3);

        ProjectionOperation project = Aggregation.project()
                .and(ConditionalOperators.ifNull("_id")
                        .then("Anonymous")).as("username")
                .andInclude("messageCount");

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                unwindMessages,
                groupByUser,
                sortByCount,
                limit,
                project
        );

        return mongoTemplate.aggregate(
                aggregation,
                "chat",
                UserMessageStatsDTO.class
        ).getMappedResults();
    }

    @Override
    public void getUsersWithoutActivityMoreThanWeek(Long chatId, String chatName) throws TelegramApiException {
        List<UserMessageStatsDTO> stats = usersWithoutActivityMoreThanWeek(chatName);

        String result = stats
                .stream()
                .map(stat -> stat.getUsername() + " -- " + stat.getLastMessageTime() + " date")
                .collect(Collectors.joining("\n"));

        messageSender.sendMessage(chatId, result);
    }

    private List<UserMessageStatsDTO> usersWithoutActivityMoreThanWeek(String chatName) {
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);

        MatchOperation matchOperation = Aggregation.match(Criteria.where("chatName")
                .is(chatName));

        UnwindOperation unwindOperation = Aggregation.unwind("messages");

        GroupOperation groupByUserWithLastMessageTime = Aggregation.group("messages.fromUser")
                .max("messages.createAt").as("lastMessageTime");

        MatchOperation filterOldUsers = Aggregation.match(
                Criteria.where("lastMessageTime").lt(weekAgo));

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "lastMessageTime");

        ProjectionOperation project = Aggregation.project()
                .and("_id").as("username")
                .and("lastMessageTime").as("lastMessageTime");

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                unwindOperation,
                groupByUserWithLastMessageTime,
                filterOldUsers,
                sortOperation,
                project
        );

        return mongoTemplate.aggregate(
                aggregation,
                "chat",
                UserMessageStatsDTO.class
        ).getMappedResults();
    }

    @Override
    public void getAverageMessagesPerDayByLastMonth(Long chatId, String chatName) throws TelegramApiException {
        List<MessageStatsDTO> averageMessages = getAverageMessagesPerDayByLastMonth(chatName);

        String result = averageMessages
                .stream()
                .map(messageStat -> messageStat.getTotalMessage() + " total message" + " \n"
                    + messageStat.getAverageMessagesPerDay() + " average message")
                .collect(Collectors.joining());

        messageSender.sendMessage(chatId, result);
    }

    private List<MessageStatsDTO> getAverageMessagesPerDayByLastMonth(String chatName) {
        UnwindOperation unwindOperation = Aggregation.unwind("messages");

        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("chatName").is(chatName)
                        .and("messages.createAt").gte(LocalDate.now().minusDays(30))
        );

        ProjectionOperation projectOperation = Aggregation.project()
                .and("messages.createAt").dateAsFormattedString("%Y-%m-%d").as("date");

        GroupOperation groupOperation = Aggregation
                .group("chatName", "date")
                .count().as("messagesPerDay");

        GroupOperation totalMessagesGroupOperation = Aggregation
                .group("chatName")
                .sum("messagesPerDay").as("totalMessages")
                .count().as("daysCount");

        ProjectionOperation avgProjectionOperation = Aggregation.project()
                .and("totalMessages").as("totalMessage")
                .andExpression("totalMessages / daysCount").as("averageMessagesPerDay");

        Aggregation aggregation = Aggregation.newAggregation(
                unwindOperation,
                matchOperation,
                projectOperation,
                groupOperation,
                totalMessagesGroupOperation,
                avgProjectionOperation
        );


        return mongoTemplate.aggregate(
                aggregation,
                "chat",
                MessageStatsDTO.class
        ).getMappedResults();
    }
}
