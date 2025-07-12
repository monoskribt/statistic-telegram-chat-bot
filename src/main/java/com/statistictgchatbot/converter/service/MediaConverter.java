package com.statistictgchatbot.converter.service;

public interface MediaConverter<S, T> {
    T convert(S media);
}
