package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.model.UserEvent;
import com.statistictgchatbot.model.submodel.Message;
import com.statistictgchatbot.repository.UserEventRepo;
import com.statistictgchatbot.service.FileService;
import com.statistictgchatbot.service.UserEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserEventServiceImpl implements UserEventService {

    private final UserEventRepo userEventRepo;
    private final FileService fileService;

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    public UserEventServiceImpl(UserEventRepo userEventRepo, FileService fileService) {
        this.userEventRepo = userEventRepo;
        this.fileService = fileService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void parseUserEventFromFile(String filePath) throws IOException {
        log.info("Start parse from file path: {}", filePath);

        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        JsonNode messagesNode = rootNode.get("messages");

        if (messagesNode == null || !messagesNode.isArray()) {
            log.warn("No 'messages' field found in the JSON file");
            return;
        }

        List<Message> messages = objectMapper.readerForListOf(Message.class).readValue(messagesNode);

        UserEvent userEvent = new UserEvent();
        userEvent.setMessages(messages);


        log.info("Parsed messages count: {}", messages.size());

        userEventRepo.save(userEvent);
        log.info("UserEvent with only messages saved.");
    }
}
