package com.statistictgchatbot.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MediaType {
    VIDEO_FILE,
    VOICE_MESSAGE,
    VIDEO_MESSAGE,
    STICKER,
    ANIMATION;

    @JsonCreator
    public static MediaType fromString(String value) {
        return MediaType.valueOf(value.toUpperCase());
    }
}
