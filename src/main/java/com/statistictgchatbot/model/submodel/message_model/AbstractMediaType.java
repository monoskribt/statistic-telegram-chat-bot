package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class AbstractMediaType {
    protected String fileId;
    protected String fileUniqueId;
    protected Integer fileSize;

    public AbstractMediaType(String fileId, String fileUniqueId, Integer fileSize) {
        this.fileId = fileId;
        this.fileUniqueId = fileUniqueId;
        this.fileSize = fileSize;
    }
}
