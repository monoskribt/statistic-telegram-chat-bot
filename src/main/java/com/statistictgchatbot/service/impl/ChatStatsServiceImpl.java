package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.dto.MessageStatsDTO;
import com.statistictgchatbot.dto.UserMessageStatsDTO;
import com.statistictgchatbot.dto.WeeklyMessageStatsDTO;
import com.statistictgchatbot.service.ChatStatsService;
import com.statistictgchatbot.service.MessageSender;
import com.statistictgchatbot.util.GeneratorStatsPicture;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    @Override
    public void prepareStatisticCountOfMessageToGraph(Long chatId, String chatName) throws TelegramApiException, IOException {
        List<WeeklyMessageStatsDTO> stats = prepareStatisticCountOfMessageToGraph(chatName);

        GeneratorStatsPicture.buildChart(stats, "src/main/resources/uploaded/stats.png");

        int totalMessages = stats
                .stream()
                .mapToInt(WeeklyMessageStatsDTO::getMessageCount)
                .sum();

        double averageMessages = stats
                .stream()
                .mapToInt(WeeklyMessageStatsDTO::getMessageCount)
                .average()
                .orElse(0);

        WeeklyMessageStatsDTO mostActiveWeek = stats
                .stream()
                .max(Comparator
                        .comparingInt(WeeklyMessageStatsDTO::getMessageCount))
                .orElse(null);

        String report = String.format(
                """
                📊 Messages report:
    
                • Total messages: %d
                • Average messages per week: %.2f
                • Most active week: %d year, %d week (%d messages)
                """,
                totalMessages,
                averageMessages,
                mostActiveWeek != null ? mostActiveWeek.getYear() : 0,
                mostActiveWeek != null ? mostActiveWeek.getWeek() : 0,
                mostActiveWeek != null ? mostActiveWeek.getMessageCount() : 0
        );

        messageSender.sendMessage(chatId, report);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new File("src/main/resources/uploaded/stats.png")));

        messageSender.sendPhoto(chatId, sendPhoto);
    }

    private List<WeeklyMessageStatsDTO> prepareStatisticCountOfMessageToGraph(String chatName) {
        UnwindOperation unwindOperation = Aggregation.unwind("messages");

        MatchOperation matchOperation = Aggregation.match(Criteria.where("chatName")
                .is(chatName));

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("year(messages.createAt)").as("year")
                .andExpression("isoWeek(messages.createAt)").as("week");

        GroupOperation groupOperation = Aggregation.group("year", "week")
                .count().as("messageCount");

        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Order.asc("_id.year"),
                Sort.Order.asc("_id.week")));

        ProjectionOperation finalProjectOperation = Aggregation.project()
                .and("_id.year").as("year")
                .and("_id.week").as("week")
                .and("messageCount").as("messageCount");

        Aggregation aggregation = Aggregation.newAggregation(
                unwindOperation,
                matchOperation,
                projectionOperation,
                groupOperation,
                sortOperation,
                finalProjectOperation
        );

        return mongoTemplate.aggregate(
                aggregation,
                "chat",
                WeeklyMessageStatsDTO.class
        ).getMappedResults();
    }
}
