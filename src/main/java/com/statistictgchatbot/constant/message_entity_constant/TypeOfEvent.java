package com.statistictgchatbot.constant.message_entity_constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypeOfEvent {
    MESSAGE,
    JOIN_MEMBER,
    LEAVE_MEMBER,
    SERVICE;

    @JsonCreator
    public static TypeOfEvent fromString(String value) {
        return TypeOfEvent.valueOf(value.toUpperCase());
    }
}
