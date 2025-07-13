package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
