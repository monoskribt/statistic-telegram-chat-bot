package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.Message;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReplyConverter implements MediaConverter<org.telegram.telegrambots.meta.api.objects.message.Message, Message> {

    @Override
    public Message convert(org.telegram.telegrambots.meta.api.objects.message.Message tgMessage) {
        Message replyStub = new Message();
        replyStub.setId(tgMessage.getMessageId());
        replyStub.setFromId(tgMessage.getFrom().getId().toString());
        replyStub.setFromUser(tgMessage.getFrom().getUserName());
        replyStub.setText(tgMessage.getText());
        replyStub.setCaption(tgMessage.getCaption());
        replyStub.setCreateAt(new Date((long) tgMessage.getDate() * 1000));

        return replyStub;
    }
}

