package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.dto.MessageDTO;
import com.statistictgchatbot.dto.MessageStatsDTO;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.model.submodel.Message;
import com.statistictgchatbot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MongoTemplate mongoTemplate;

    @Override
    public void appendMessage(String chatId, Message message) {
        Query query = new Query(Criteria.where("chatId").is(chatId));
        Update update = new Update().push("messages", message);
        mongoTemplate.updateFirst(query, update, Chat.class);
    }

    @Override
    public Message getMessageByChatAndMessageId(Long chatId, int messageId) {
        MatchOperation matchOperationChat = Aggregation.match(Criteria.where("chatId").is(chatId));
        UnwindOperation unwindOperation = Aggregation.unwind("messages");
        MatchOperation matchOperationMessage = Aggregation.match(Criteria.where("messages.id").is(messageId));
        ProjectionOperation projectMessage = Aggregation.project("messages");

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperationChat,
                unwindOperation,
                matchOperationMessage,
                projectMessage
        );

        return Objects.requireNonNull(mongoTemplate.aggregate(
                aggregation,
                "chat",
                MessageDTO.class
        ).getUniqueMappedResult()).getMessage();
    }
}
