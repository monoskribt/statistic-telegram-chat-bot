package com.statistictgchatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistictgchatbot.constant.BotCommands;
import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.converter.MessageConverter;
import com.statistictgchatbot.exception.FileDownloadException;
import com.statistictgchatbot.model.Chat;
import com.statistictgchatbot.service.*;
import com.statistictgchatbot.util.CommandUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Optional;

import static com.statistictgchatbot.constant.Constants.PATH_TO_UPLOADED_FILE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateReceivedServiceImpl implements UpdateReceivedService {

    private final ChatAnalytics chatAnalytics;
    private final ChatManagementService chatManagementService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final MessageConverter messageConverter;
    private final FileService fileService;
    private final S3Service s3Service;
    private final MessageSender messageSender;

    @Override
    public void updateReceivedMessageByCommand(String messageText, Long chatId) {
        String[] splitMessage = messageText.split("\\s+");
        String chatName = splitMessage[splitMessage.length - 1];

        String command = CommandUtils.getCommandFromMessage(messageText);

        try {
            switch (command) {
                case BotCommands.MOST_ACTIVE_USERS, BotCommands.MOST_INACTIVE_USERS ->
                        chatAnalytics.getUserActivity(chatId, chatName, messageText);
                case BotCommands.INACTIVE_BY_WEEK ->
                        chatAnalytics.getInactiveUsersForAWeek(chatId, chatName);
                case BotCommands.AVERAGE_MESSAGE_PER_DAY ->
                        chatAnalytics.getAverageMessagePerDay(chatId, chatName);
                case BotCommands.CHAT_REPORT ->
                        chatAnalytics.getChatReport(chatId, chatName);
                case BotCommands.UNKNOWN_COMMAND ->
                        chatAnalytics.sendDefaultMessage(chatId);
            }
        } catch (TelegramApiException | IOException e) {
            log.warn("Problem with chat name or method");
        }
    }

    @Override
    public void updateReceivedMessage(String chatId, Message message, TypeOfEvent typeOfEvent, MediaType mediaType) {
        if(message.getChat().isUserChat()) {
            return;
        }

        com.statistictgchatbot.model.submodel.Message messageToDb =
                messageConverter.convertMessageTGEntityToDBEntityForMessagesEvents(
                        message,
                        typeOfEvent,
                        mediaType);
        Optional.of(chatId)
                .filter(chatService::chatIsExist)
                .ifPresentOrElse(
                        id -> {
                            messageService.appendMessage(id, messageToDb);
                            log.info("Added message: {}", messageToDb);
                        },
                        () -> {
                            try {
                                chatManagementService.saveChatFromMessage(message, messageToDb);
                                log.info("Saved new chat in Data Base and added the message: {}", messageToDb);
                            } catch (JsonProcessingException e) {
                                log.error("Failed during serialization message from TG");
                            }
                        }
                );
    }

    @Override
    public void updateReceivedDocument(Document document, Long chatId) {
        String fieldId = document.getFileId();
        String fileName = document.getFileName();

        try {
            documentProcessing(fileName, fieldId, chatId);
        } catch (TelegramApiException e) {
            throw new FileDownloadException("Failed during download file");
        }
    }

    private void documentProcessing(String fileName, String fieldId, Long chatId) throws TelegramApiException {
        try {
            fileService.uploadFile(fileName, fieldId);
            s3Service.uploadFileToS3(fileName, PATH_TO_UPLOADED_FILE);
            fileService.deleteFileFromLocal(fileName);
        } catch (IOException e) {
            messageSender.sendMessage(chatId, "Failed while parsing file. " +
                    "Check your file and try again later");
            throw new FileDownloadException("Failed to download file");
        }
    }
}
