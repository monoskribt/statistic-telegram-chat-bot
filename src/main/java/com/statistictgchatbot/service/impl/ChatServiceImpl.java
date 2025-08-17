package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.exception.ChatNotFoundException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.repository.ChatRepo;
import com.statistictgchatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepo chatRepo;

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
    public String createHashIdForChat(Chat chat, ObjectMapper objectMapper) throws JsonProcessingException {
        return DigestUtils.md5DigestAsHex(objectMapper.writeValueAsBytes(chat));
    }
}
