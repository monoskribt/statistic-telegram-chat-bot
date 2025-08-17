package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ChatMemberEvent {
    private String userId;
    private String username;
    private Date date;
}
