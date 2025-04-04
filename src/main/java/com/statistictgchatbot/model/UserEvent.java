package com.statistictgchatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.statistictgchatbot.constant.TypeOfEvent;
import com.statistictgchatbot.model.submodel.Message;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserEvent {

    @Id
    private UUID id;

    @JsonProperty(value = "id")
    private String idEvent;

    @JsonProperty(value = "type")
    private TypeOfEvent type;

    private List<Message> message;
}
