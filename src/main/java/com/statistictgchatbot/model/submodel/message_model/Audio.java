package com.statistictgchatbot.model.submodel.message_model;

public class Audio extends AbstractMediaType {
    private Integer duration;
    private String fileName;
    private String title;

    public Audio(String fileId, String fileUniqueId, Integer fileSize, Integer duration, String fileName, String title) {
        super(fileId, fileUniqueId, fileSize);
        this.duration = duration;
        this.fileName = fileName;
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
