package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.exception.ChatNotFoundException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.model.submodel.Message;
import com.statistictgchatbot.repository.ChatRepo;
import com.statistictgchatbot.service.ChatService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepo chatRepo;
    private final MongoTemplate mongoTemplate;

    public ChatServiceImpl(ChatRepo chatRepo, MongoTemplate mongoTemplate) {
        this.chatRepo = chatRepo;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveChat(Chat chatToSave) {
        chatRepo.save(chatToSave);
    }

    @Override
    public void deleteChat(Chat chatToDelete) {
        chatRepo.delete(chatToDelete);
    }

    @Override
    public Chat getChatByChatId(String chatId) {
        return chatRepo.findChatByChatId(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat with id " + chatId + "is not present"));
    }

    @Override
    public boolean chatIsExist(String chatId) {
        return chatRepo.existsChatByChatId(chatId);
    }

    @Override
    public void appendMessage(String chatId, Message message) {
        Query query = new Query(Criteria.where("chatId").is(chatId));
        Update update = new Update().push("messages", message);
        mongoTemplate.updateFirst(query, update, Chat.class);
    }

    @Override
    public String createHashIdForChat(Chat chat, ObjectMapper objectMapper) throws JsonProcessingException {
        return DigestUtils.md5DigestAsHex(objectMapper.writeValueAsBytes(chat));
    }
}
