package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class ReplyToMessage {
    @JsonProperty("reply_to_message_id")
    private Integer replyToMessageId;

    @JsonProperty("reply_to_user")
    private String replyToUser;

    @JsonProperty("reply_to_text")
    private String replyToText;
}
