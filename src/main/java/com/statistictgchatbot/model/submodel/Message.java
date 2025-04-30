package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.statistictgchatbot.constant.MediaType;
import com.statistictgchatbot.constant.TypeOfEvent;
import com.statistictgchatbot.util.TextDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
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

    @JsonProperty(value = "photo")
    private String photo;

    @JsonProperty(value = "media_type")
    private MediaType mediaType;

    @JsonProperty(value = "text")
    @JsonDeserialize(using = TextDeserialize.class)
    private String text;

    @JsonProperty(value = "reactions")
    private List<Reaction> reactions;
}
