package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reaction {

    @JsonProperty(value = "count")
    private int count;

    @JsonProperty(value = "emoji")
    private String emoji;

    @JsonProperty(value = "recent")
    private List<ReactionDetail> reactionDetails;
}
