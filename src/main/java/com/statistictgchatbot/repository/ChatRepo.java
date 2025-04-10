package com.statistictgchatbot.repository;

import com.statistictgchatbot.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends MongoRepository<Chat, String> {
}
