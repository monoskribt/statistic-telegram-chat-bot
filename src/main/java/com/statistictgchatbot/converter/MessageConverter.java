package com.statistictgchatbot.converter;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.model.submodel.Message;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageConverter {
    public Message convertMessageTGEntityToDBEntityForMessagesEvents(
            org.telegram.telegrambots.meta.api.objects.message.Message messageFromTg,
            TypeOfEvent typeOfEvent,
            MediaType mediaType) {
        Message messageToDb = new Message();

        messageToDb.setId(Integer.parseInt(String.valueOf(messageFromTg.getMessageId())));

        messageToDb.setType(typeOfEvent);

        messageToDb.setCreateAt(new Date());

        messageToDb.setFromUser(messageFromTg.getFrom().getUserName());

        if(messageFromTg.getEditDate() != null) {
           messageToDb.setEditedAt(messageFromTg.getEditDate());
        }

        messageFromTg.setFrom(messageFromTg.getFrom());

        messageToDb.setFromId(messageFromTg.getFrom().getId().toString());

        if(messageFromTg.getPhoto() != null) {
            messageToDb.setPhoto(messageFromTg.getPhoto().toString());
        }

        messageToDb.setMediaType(mediaType);

        messageToDb.setText(messageFromTg.getText());

        return messageToDb;
    }
}
