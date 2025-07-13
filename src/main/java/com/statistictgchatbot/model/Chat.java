package com.statistictgchatbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.statistictgchatbot.model.submodel.Message;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chat {

    @JsonIgnore
    @Id
    private String id;

    @JsonProperty(value = "name")
    private String chatName;

    @JsonProperty(value = "id")
    private String chatId;

    @JsonProperty(value = "messages")
    private List<Message> messages;
}
