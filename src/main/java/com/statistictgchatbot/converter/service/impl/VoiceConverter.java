package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.message_model.Voice;
import org.springframework.stereotype.Component;

@Component
public class VoiceConverter implements MediaConverter<org.telegram.telegrambots.meta.api.objects.Voice, Voice> {

    @Override
    public com.statistictgchatbot.model.submodel.message_model.Voice convert(org.telegram.telegrambots.meta.api.objects.Voice voice) {
        return new com.statistictgchatbot.model.submodel.message_model.Voice(
                voice.getFileId(),
                voice.getFileUniqueId(),
                Math.toIntExact(voice.getFileSize()),
                voice.getDuration()
        );
    }
}
