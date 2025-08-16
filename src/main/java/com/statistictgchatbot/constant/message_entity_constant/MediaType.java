package com.statistictgchatbot.constant.message_entity_constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MediaType {
    TEXT,
    REPLY,
    PHOTO,
    AUDIO,
    VOICE_MESSAGE,
    VIDEO_MESSAGE,
    VIDEO_FILE,
    STICKER,
    ANIMATION;

    @JsonCreator
    public static MediaType fromString(String value) {
        return MediaType.valueOf(value.toUpperCase());
    }
}
