package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Reaction {

    @JsonProperty(value = "emoji")
    private String emoji;

    @JsonProperty(value = "count")
    private int count;

    @JsonProperty(value = "recent")
    private List<ReactionDetail> reactionDetails;
}
