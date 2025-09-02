package com.statistictgchatbot.repository;

import com.statistictgchatbot.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepo extends MongoRepository<Chat, String> {
    Optional<Chat> findChatByChatId(String chatId);

    boolean existsChatByChatId(String chatId);

    boolean existsChatByChatName(String chatName);

    List<Chat> findAll();
}
