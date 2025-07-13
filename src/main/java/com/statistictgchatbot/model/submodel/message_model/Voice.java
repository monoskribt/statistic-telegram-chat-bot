package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Voice extends AbstractMediaType {
    protected Integer duration;

    public Voice(String fileId, String fileUniqueId, Integer fileSize, Integer duration) {
        super(fileId, fileUniqueId, fileSize);
        this.duration = duration;
    }

}
