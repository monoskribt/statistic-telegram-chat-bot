package com.statistictgchatbot.model.submodel.message_model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonCreator
    public Photo(String filePath) {
        super(null, null, null);
        this.filePath = filePath;
    }

    @JsonValue
    public String getFilePath() {
        return filePath;
    }

}
