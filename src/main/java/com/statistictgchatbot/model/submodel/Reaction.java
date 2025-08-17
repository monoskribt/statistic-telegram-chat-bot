package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reaction {

    @JsonProperty(value = "count")
    private int count;

    @JsonProperty(value = "emoji")
    private String emoji;

    @JsonProperty(value = "recent")
    private List<ReactionDetail> reactionDetails;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public List<ReactionDetail> getReactionDetails() {
        return reactionDetails;
    }

    public void setReactionDetails(List<ReactionDetail> reactionDetails) {
        this.reactionDetails = reactionDetails;
    }
}
