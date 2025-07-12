package com.statistictgchatbot.model.submodel.message_model;

public class Photo extends AbstractMediaType {
    protected String filePath;

    public Photo(String fileId, String fileUniqueId, Integer fileSize, String filePath) {
        super(fileId, fileUniqueId, fileSize);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
