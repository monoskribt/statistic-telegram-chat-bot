package com.statistictgchatbot.model.submodel.message_model;

public class Animation extends AbstractMediaType {
    protected String mimeType;

    public Animation(String fileId, String fileUniqueId, Integer fileSize, String mimeType) {
        super(fileId, fileUniqueId, fileSize);
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
