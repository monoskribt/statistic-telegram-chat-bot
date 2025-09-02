package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Photo extends AbstractMediaType {
    protected String filePath;

    public Photo(String fileId, String fileUniqueId, Integer fileSize, String filePath) {
        super(fileId, fileUniqueId, fileSize);
        this.filePath = filePath;
    }
}
