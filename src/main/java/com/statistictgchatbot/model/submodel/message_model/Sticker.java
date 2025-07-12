package com.statistictgchatbot.model.submodel.message_model;

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

    public String getCustomEmojiId() {
        return customEmojiId;
    }

    public void setCustomEmojiId(String customEmojiId) {
        this.customEmojiId = customEmojiId;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
