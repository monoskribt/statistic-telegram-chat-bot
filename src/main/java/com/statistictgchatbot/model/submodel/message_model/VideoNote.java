package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoNote extends AbstractMediaType {
    protected Integer duration;
    protected Integer length;

    public VideoNote(String fileId, String fileUniqueId, Integer fileSize, Integer duration, Integer length) {
        super(fileId, fileUniqueId, fileSize);
        this.duration = duration;
        this.length = length;
    }

}
