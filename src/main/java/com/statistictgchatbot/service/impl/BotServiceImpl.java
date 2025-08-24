package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.creation.MessagesObjectsCreation;
import com.statistictgchatbot.model.submodel.Reaction;
import com.statistictgchatbot.service.BotService;
import com.statistictgchatbot.service.ChatService;
import com.statistictgchatbot.service.MessageService;
import com.statistictgchatbot.service.UpdateReceivedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.telegram.telegrambots.meta.api.objects.chatmember.MemberStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotServiceImpl implements BotService {
    private final UpdateReceivedService updateReceivedService;
    private final MessageService messageService;
    private final ChatService chatService;
    private final MessagesObjectsCreation messagesObjectsCreation;

    @Override
    public void handleBotEvents(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            Message message = update.getMessage();

            if (message.getLeftChatMember() != null ||
                    (message.getNewChatMembers() != null && !message.getNewChatMembers().isEmpty())) {
                handleChatMember(update, String.valueOf(chatId));
                return;
            }
            handleCommand(update, message, chatId);
            handleMessage(message, chatId);
            handleDocument(update, chatId);
        }
        if(update.hasEditedMessage()) {
            handleEditedMessage(update);
        }
        if (update.getMessageReaction() != null) {
            handleReaction(update);
        }
    }

    public void handleCommand(Update update, Message message, Long chatId) {
        if (message.hasText() && message.getText().startsWith("/")) {
            String messageText = update.getMessage().getText();
            updateReceivedService.updateReceivedMessageByCommand(messageText, chatId);
        }
    }

    private void handleMessage(Message message, Long chatId) {
        if(message.hasText() && !message.isReply()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.TEXT);
        }
        if(message.isReply()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.REPLY);
        }
        if(message.hasAudio()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.AUDIO);
        }
        if(message.hasVoice()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.VOICE_MESSAGE);
        }
        if(message.hasVideo()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.VIDEO_FILE);
        }
        if(message.hasVideoNote()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.VIDEO_MESSAGE);
        }
        if(message.hasPhoto()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.PHOTO);
        }
        if(message.hasAnimation()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.ANIMATION);
        }
        if(message.hasSticker()) {
            updateReceivedService.updateReceivedMessage(
                    String.valueOf(chatId),
                    message,
                    TypeOfEvent.MESSAGE,
                    MediaType.STICKER);
        }
    }

    public void handleEditedMessage(Update update) {
        Message editedMessage = update.getEditedMessage();
        chatService.chatIsExist(String.valueOf(editedMessage.getChatId()));
        if(chatService.chatIsExist(String.valueOf(editedMessage.getChatId()))) {
            try {
                com.statistictgchatbot.model.submodel.Message message = messageService
                        .getMessageByChatAndMessageId(editedMessage.getChatId(), editedMessage.getMessageId());
                messagesObjectsCreation.createEditedMessage(message, editedMessage);
                messageService.updateMessage(String.valueOf(editedMessage.getChatId()), editedMessage.getMessageId(), message);
            } catch (Exception e) {
                log.warn("Exception: {}", e.getMessage());
            }
        }
    }

    private void handleReaction(Update update) {
        MessageReactionUpdated reactionUpdated = update.getMessageReaction();
        if (reactionUpdated == null) return;

        Long chatId = reactionUpdated.getChat().getId();
        int messageId = reactionUpdated.getMessageId();

        Optional<com.statistictgchatbot.model.submodel.Message> optionalMessage =
                Optional.ofNullable(messageService.getMessageByChatAndMessageId(chatId, messageId));

        if (optionalMessage.isEmpty()) return;

        com.statistictgchatbot.model.submodel.Message messageFromDb = optionalMessage.get();
        List<Reaction> reactions = messageFromDb.getReactions();
        if (reactions == null) {
            reactions = new ArrayList<>();
            messageFromDb.setReactions(reactions);
        }

        for (ReactionType reactionType : reactionUpdated.getNewReaction()) {
            messagesObjectsCreation.createCreationType(reactionType, reactionUpdated, reactions);
            messageFromDb.setReactions(reactions);
        }

        messageFromDb.setId(messageId);
        messageService.updateMessage(String.valueOf(chatId), messageId, messageFromDb);
    }

    private void handleChatMember(Update update, String chatId) {
        com.statistictgchatbot.model.submodel.Message dbMessage = new com.statistictgchatbot.model.submodel.Message();
        if (update.hasMessage()) {
            Message tgMessage = update.getMessage();
            if (tgMessage.getNewChatMembers() != null && !tgMessage.getNewChatMembers().isEmpty()) {
                for (User user : tgMessage.getNewChatMembers()) {
                    messagesObjectsCreation.createChatMemberEvent(
                            dbMessage,
                            tgMessage,
                            user,
                            TypeOfEvent.JOIN_MEMBER);
                }
            } else if (tgMessage.getLeftChatMember() != null) {
                messagesObjectsCreation.createChatMemberEvent(
                        dbMessage,
                        tgMessage,
                        tgMessage.getLeftChatMember(),
                        TypeOfEvent.LEAVE_MEMBER);
            }
        }

        messageService.appendMessage(chatId, dbMessage);
    }

    private void handleDocument(Update update, Long chatId) {
        if (update.getMessage().hasDocument()) {
            org.telegram.telegrambots.meta.api.objects.chat.Chat tgChat = update.getMessage().getChat();

            if (tgChat.isUserChat()) {
                Document document = update.getMessage().getDocument();
                updateReceivedService.updateReceivedDocument(document, chatId);
            }
        }
    }
}
