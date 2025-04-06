package com.statistictgchatbot.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypeOfEvent {
    MESSAGE,
    SERVICE;

    @JsonCreator
    public static TypeOfEvent fromString(String value) {
        return TypeOfEvent.valueOf(value.toUpperCase());
    }
}
