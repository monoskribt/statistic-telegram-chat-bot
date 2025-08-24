package com.statistictgchatbot.creation;

import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.model.submodel.Reaction;
import com.statistictgchatbot.model.submodel.ReactionDetail;
import com.statistictgchatbot.model.submodel.message_model.ChatMemberEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;

import java.util.Date;
import java.util.List;

@Component
public class MessagesObjectsCreation {

    public void createChatMemberEvent(com.statistictgchatbot.model.submodel.Message message,
                                      Message tgMessage,
                                      User user,
                                      TypeOfEvent typeOfEvent) {
        message.setId(tgMessage.getMessageId());
        message.setType(typeOfEvent);

        ChatMemberEvent chatMemberEvent = new ChatMemberEvent();
        chatMemberEvent.setUserId(String.valueOf(user.getId()));
        chatMemberEvent.setUsername(user.getFirstName());
        chatMemberEvent.setDate(new Date());

        message.setChatMemberEvent(chatMemberEvent);
    }

    public void createCreationType(ReactionType reactionType, MessageReactionUpdated reactionUpdated, List<Reaction> reactions) {
        ReactionDetail detail = new ReactionDetail();
        detail.setFromUserId(reactionUpdated.getUser().getId().toString());
        detail.setDate(new Date());

        Reaction reaction = new Reaction();
        reaction.setEmoji(reactionType.toString());
        reaction.setReactionDetails(List.of(detail));
        reaction.setCount(reactions.size() + 1);
        reactions.add(reaction);
    }

    public void createEditedMessage(com.statistictgchatbot.model.submodel.Message message, Message editedMessage) {
        message.setCaption(editedMessage.getCaption());
        message.setText(editedMessage.getText());
    }
}
