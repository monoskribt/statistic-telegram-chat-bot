package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Animation extends AbstractMediaType {
    protected String mimeType;

    public Animation(String fileId, String fileUniqueId, Integer fileSize, String mimeType) {
        super(fileId, fileUniqueId, fileSize);
        this.mimeType = mimeType;
    }

}
