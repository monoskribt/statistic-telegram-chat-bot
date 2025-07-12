package com.statistictgchatbot.model.submodel.message_model;

public class Video extends AbstractMediaType {
    protected String fileName;
    protected Integer duration;

    public Video(String fileId, String fileUniqueId, Integer fileSize, String fileName, Integer duration) {
        super(fileId, fileUniqueId, fileSize);
        this.fileName = fileName;
        this.duration = duration;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
