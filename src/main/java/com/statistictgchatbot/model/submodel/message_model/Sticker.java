package com.statistictgchatbot.model.submodel.message_model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sticker extends AbstractMediaType {
    protected String customEmojiId;
    protected String emoji;
    protected String type;

    public Sticker(String fileId, String fileUniqueId, Integer fileSize, String customEmojiId, String emoji, String type) {
        super(fileId, fileUniqueId, fileSize);
        this.customEmojiId = customEmojiId;
        this.emoji = emoji;
        this.type = type;
    }

}
