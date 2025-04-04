package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReactionDetail {

    @JsonProperty(value = "from")
    private String fromUser;

    @JsonProperty(value = "from_id")
    private String fromUserId;

    @JsonProperty(value = "date")
    private Date date;
}
