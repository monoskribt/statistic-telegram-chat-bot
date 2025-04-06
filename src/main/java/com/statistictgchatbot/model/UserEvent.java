package com.statistictgchatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.statistictgchatbot.model.submodel.Message;
import org.springframework.data.annotation.Id;

import java.util.List;

public class UserEvent {

    @Id
    private String id;

    @JsonProperty(value = "messages")
    private List<Message> messages;

    public String getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
