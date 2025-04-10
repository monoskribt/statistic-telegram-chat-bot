package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.model.submodel.Message;
import com.statistictgchatbot.service.ChatService;
import com.statistictgchatbot.service.ChatStatsService;
import com.statistictgchatbot.service.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ChatStatsServiceImpl implements ChatStatsService {
    private final ChatService chatService;
    private final MessageSender messageSender;

    public ChatStatsServiceImpl(ChatService chatService, MessageSender messageSender) {
        this.chatService = chatService;
        this.messageSender = messageSender;
    }

    private final Map<String, Integer> mostActiveUsers = new ConcurrentHashMap<>();


    @Override
    public void getMostActiveUsers(Long chatId, String chatName) throws TelegramApiException {
        findMostActiveUsers(chatName);

        messageSender.sendMessage(chatId, mostActiveUsers.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(user -> user.getKey() + " -- " + user.getValue() + " messages")
                .collect(Collectors.joining("\n")));
    }


    private void findMostActiveUsers(String chatName) {
        Chat chatByName = chatService.getChatByName(chatName);
        List<Message> messageList = chatByName.getMessages();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            for(Message message : messageList) {
                String user = message.getFromUser();
                if(user != null) {
                    mostActiveUsers.merge(user, 1, Integer::sum);
                }
            }
        }, executorService);

        completableFuture.join();
        executorService.shutdown();
    }
}
