package com.statistictgchatbot.model.submodel.message_model;

public class VideoNote extends AbstractMediaType {
    protected Integer duration;
    protected Integer length;

    public VideoNote(String fileId, String fileUniqueId, Integer fileSize, Integer duration, Integer length) {
        super(fileId, fileUniqueId, fileSize);
        this.duration = duration;
        this.length = length;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
