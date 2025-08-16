package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.statistictgchatbot.constant.message_entity_constant.Action;
import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.model.submodel.message_model.*;
import com.statistictgchatbot.util.TextDeserialize;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Message {
    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "type")
    private TypeOfEvent type;

    @JsonProperty(value = "date")
    private Date createAt;

    @JsonProperty(value = "edited")
    private Integer editedAt;

    @JsonProperty(value = "actor")
    private String actor;

    @JsonProperty(value = "actor_id")
    private String actorId;

    @JsonProperty(value = "action")
    private Action action;

    @JsonProperty(value = "members")
    private List<String> members;

    @JsonProperty(value = "from")
    private String fromUser;

    @JsonProperty(value = "from_id")
    private String fromId;

    @JsonProperty(value = "reply_to_message_id")
    private Message replyToMessage;

    @JsonProperty(value = "photo")
    private Photo photo;

    @JsonProperty(value = "audio")
    private Audio audio;

    @JsonProperty(value = "voice")
    private Voice voice;

    @JsonProperty(value = "video")
    private Video video;

    @JsonProperty(value = "videoNote")
    private VideoNote videoNote;

    @JsonProperty(value = "animation")
    private Animation animation;

    @JsonProperty(value = "sticker")
    private Sticker sticker;

    @JsonProperty(value = "media_type")
    private MediaType mediaType;

    @JsonProperty(value = "text")
    @JsonDeserialize(using = TextDeserialize.class)
    private String text;

    @JsonProperty(value = "caption")
    private String caption;

    @JsonProperty(value = "reactions")
    private List<?> reactions;
}
