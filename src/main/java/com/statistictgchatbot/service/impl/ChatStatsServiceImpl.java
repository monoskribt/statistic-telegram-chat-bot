package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.dto.UserMessageStatsDTO;
import com.statistictgchatbot.service.ChatService;
import com.statistictgchatbot.service.ChatStatsService;
import com.statistictgchatbot.service.MessageSender;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
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

    private final Map<String, Integer> mostActiveUsers = new ConcurrentHashMap<>();


    @Override
    public void getMostActiveUsers(Long chatId, String chatName) throws TelegramApiException {
        List<UserMessageStatsDTO> stats = findMostActiveUsers(chatName);

        String result = stats.stream()
                .map(stat -> stat.getUsername() + " -- " + stat.getMessageCount() + " messages")
                .collect(Collectors.joining("\n"));

        messageSender.sendMessage(chatId, result);
    }


    private List<UserMessageStatsDTO> findMostActiveUsers(String chatName) {
        MatchOperation matchOperation = Aggregation
                .match(Criteria.where("chatName").is(chatName));

        UnwindOperation unwindMessages = Aggregation.unwind("messages");

        GroupOperation groupByUser = Aggregation
                .group("messages.fromUser")
                .count()
                .as("messageCount");

        SortOperation sortByCount = Aggregation
                .sort(Sort.by(Sort.Direction.DESC, "messageCount"));

        ProjectionOperation project = Aggregation.project()
                .and("_id").as("username")
                .andInclude("messageCount");

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                unwindMessages,
                groupByUser,
                sortByCount,
                project
        );

        return mongoTemplate.aggregate(
                aggregation,
                "chat",
                UserMessageStatsDTO.class
        ).getMappedResults();
    }
}
