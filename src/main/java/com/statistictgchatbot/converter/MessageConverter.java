package com.statistictgchatbot.converter;

import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.model.submodel.Message;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageConverter {
    public Message convertMessageTGEntityToDBEntity(
            org.telegram.telegrambots.meta.api.objects.message.Message messageFromTg,
            TypeOfEvent type) {
        Message messageToDb = new Message();
        messageToDb.setId(Integer.parseInt(String.valueOf(messageFromTg.getMessageId())));
        messageToDb.setType(type);
        messageToDb.setCreateAt(new Date());
        messageToDb.setFromUser(messageFromTg.getFrom().getUserName());
        messageToDb.setFromId(messageFromTg.getFrom().getId().toString());
        messageToDb.setText(messageFromTg.getText());
        return messageToDb;
    }

}
