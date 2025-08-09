package com.statistictgchatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.statistictgchatbot.model.submodel.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    @JsonProperty(value = "messages")
    private Message message;
}
