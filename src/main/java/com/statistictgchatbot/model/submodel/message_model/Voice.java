package com.statistictgchatbot.model.submodel.message_model;

public class Voice extends AbstractMediaType {
    protected Integer duration;

    public Voice(String fileId, String fileUniqueId, Integer fileSize, Integer duration) {
        super(fileId, fileUniqueId, fileSize);
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
