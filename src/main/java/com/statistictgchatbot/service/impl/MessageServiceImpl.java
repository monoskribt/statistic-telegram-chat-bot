package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.model.submodel.Message;
import com.statistictgchatbot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    public void updateMessage(String chatId, int messageId, Message updatedMessage) {
        Query query = new Query(Criteria.where("chatId").is(chatId).and("messages._id").is(messageId));
        Update update = new Update().set("messages.$", updatedMessage);
        mongoTemplate.updateFirst(query, update, Chat.class);
    }

    @Override
    public Message getMessageByChatAndMessageId(Long chatId, int messageId) {
        MatchOperation matchOperationChat = Aggregation.match(Criteria.where("chatId").is(String.valueOf(chatId)));
        UnwindOperation unwindOperation = Aggregation.unwind("messages");
        MatchOperation matchOperationMessage = Aggregation.match(Criteria.where("messages._id").is(messageId));

        ProjectionOperation projectionOperation = Aggregation.project()
                .and("messages._id").as("_id")
                .and("messages.type").as("type")
                .and("messages.createAt").as("createAt")
                .and("messages.editedAt").as("editedAt")
                .and("messages.actor").as("actor")
                .and("messages.actorId").as("actorId")
                .and("messages.action").as("action")
                .and("messages.members").as("members")
                .and("messages.fromUser").as("fromUser")
                .and("messages.fromId").as("fromId")
                .and("messages.photo").as("photo")
                .and("messages.audio").as("audio")
                .and("messages.voice").as("voice")
                .and("messages.video").as("video")
                .and("messages.videoNote").as("videoNote")
                .and("messages.animation").as("animation")
                .and("messages.sticker").as("sticker")
                .and("messages.mediaType").as("mediaType")
                .and("messages.text").as("text")
                .and("messages.caption").as("caption")
                .and("messages.reactions").as("reactions");

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperationChat,
                unwindOperation,
                matchOperationMessage,
                projectionOperation
        );

        return mongoTemplate.aggregate(aggregation, "chat", Message.class)
                .getUniqueMappedResult();
    }
}
