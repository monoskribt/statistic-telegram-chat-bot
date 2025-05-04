package com.statistictgchatbot.constant.message_entity_constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Action {
    MIGRATE_FROM_GROUP,
    ADD_MEMBERS,
    REMOVE_MEMBERS,
    INVITE_MEMBERS,
    JOIN_BY_REQUEST,
    PIN_MESSAGE,
    UNPINNED_MESSAGE,
    CHANGE_TITLE,
    CHANGE_PHOTO,
    DELETE_PHOTO;

    @JsonCreator
    public static Action fromString(String value) {
        return Action.valueOf(value.toUpperCase());
    }
}
