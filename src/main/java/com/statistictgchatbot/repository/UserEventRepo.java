package com.statistictgchatbot.repository;

import com.statistictgchatbot.model.UserEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEventRepo extends MongoRepository<UserEvent, String> {
}
