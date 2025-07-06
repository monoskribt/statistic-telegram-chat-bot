package com.statistictgchatbot.model.submodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.statistictgchatbot.constant.message_entity_constant.Action;
import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
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

    @JsonProperty(value = "photo")
    private String photo;

    @JsonProperty(value = "media_type")
    private MediaType mediaType;

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

    public Integer getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Integer editedAt) {
        this.editedAt = editedAt;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
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

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
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
