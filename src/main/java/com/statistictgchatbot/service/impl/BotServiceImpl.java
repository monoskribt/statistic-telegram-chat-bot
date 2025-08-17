package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.model.submodel.Reaction;
import com.statistictgchatbot.model.submodel.ReactionDetail;
import com.statistictgchatbot.model.submodel.message_model.ChatMemberEvent;
import com.statistictgchatbot.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void handleBotEvents(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            Message message = update.getMessage();

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
        if(update.hasChatMember()) {
            handleChatMember(update);
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
                createEditedMessage(message, editedMessage);
                messageService.updateMessage(String.valueOf(editedMessage.getChatId()), editedMessage.getMessageId(), message);
            } catch (Exception e) {
                log.warn("Exception: {}", e.getMessage());
            }
        }
    }

    private void createEditedMessage(com.statistictgchatbot.model.submodel.Message message, Message editedMessage) {
        message.setCaption(editedMessage.getCaption());
        message.setText(editedMessage.getText());
        message.setEditedAt(editedMessage.getEditDate());
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

        for (ReactionType rt : reactionUpdated.getNewReaction()) {
            ReactionDetail detail = new ReactionDetail();
            detail.setFromUserId(reactionUpdated.getUser().getId().toString());
            detail.setDate(new Date());

            Reaction reaction = new Reaction();
            reaction.setEmoji(rt.toString());
            reaction.setReactionDetails(List.of(detail));
            reaction.setCount(reactions.size() + 1);
            reactions.add(reaction);

            messageFromDb.setReactions(reactions);
        }

        messageService.updateMessage(String.valueOf(chatId), messageId, messageFromDb);
    }

    private static void handleChatMember(Update update) {
        ChatMemberUpdated chatMemberUpdated = update.getChatMember();

        com.statistictgchatbot.model.submodel.Message message = new com.statistictgchatbot.model.submodel.Message();

        switch (chatMemberUpdated.getNewChatMember().getStatus()) {
            case MEMBER -> createChatMemberEvent(message, chatMemberUpdated, TypeOfEvent.JOIN_MEMBER);
            case LEFT, KICKED -> createChatMemberEvent(message, chatMemberUpdated, TypeOfEvent.LEAVE_MEMBER);
        }
    }

    private static void createChatMemberEvent(com.statistictgchatbot.model.submodel.Message message, ChatMemberUpdated chatMemberUpdated, TypeOfEvent joinMember) {
        message.setId(chatMemberUpdated.getNewChatMember().getUser().hashCode());
        message.setType(joinMember);

        ChatMemberEvent chatMemberEvent = new ChatMemberEvent();
        chatMemberEvent.setUserId(chatMemberEvent.getUserId());
        chatMemberEvent.setUsername(chatMemberEvent.getUsername());
        chatMemberEvent.setDate(new Date());

        message.setChatMemberEvent(chatMemberEvent);
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
