package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Video extends AbstractMediaType {
    protected String fileName;
    protected Integer duration;

    public Video(String fileId, String fileUniqueId, Integer fileSize, String fileName, Integer duration) {
        super(fileId, fileUniqueId, fileSize);
        this.fileName = fileName;
        this.duration = duration;
    }

}
