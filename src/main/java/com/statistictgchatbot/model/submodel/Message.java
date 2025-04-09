package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.statistictgchatbot.constant.TypeOfEvent;
import com.statistictgchatbot.util.TextDeserialize;

import java.util.Date;
import java.util.List;

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

    @JsonProperty(value = "photo")
    private String photo;

    @JsonProperty(value = "text")
    @JsonDeserialize(using = TextDeserialize.class)
    private String text;

    @JsonProperty(value = "reactions")
    private List<Reaction> reactions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeOfEvent getType() {
        return type;
    }

    public void setType(TypeOfEvent type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }
}
