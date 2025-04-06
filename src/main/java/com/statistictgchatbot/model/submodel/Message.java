package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.statistictgchatbot.constant.TypeOfEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "type")
    private TypeOfEvent type;

    @JsonProperty(value = "date")
    private Date createAt;

    @JsonProperty(value = "edited")
    private Date editedAt;

    @JsonProperty(value = "from")
    private String fromUser;

    @JsonProperty(value = "from_id")
    private String fromId;

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "reactions")
    private List<Reaction> reactions;
}
