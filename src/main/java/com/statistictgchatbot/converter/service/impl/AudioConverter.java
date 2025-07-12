package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.message_model.Audio;
import org.springframework.stereotype.Component;

@Component
public class AudioConverter implements MediaConverter<org.telegram.telegrambots.meta.api.objects.Audio, Audio> {
    @Override
    public com.statistictgchatbot.model.submodel.message_model.Audio convert(org.telegram.telegrambots.meta.api.objects.Audio audio) {
        return new com.statistictgchatbot.model.submodel.message_model.Audio(
                audio.getFileId(),
                audio.getFileUniqueId(),
                Math.toIntExact(audio.getFileSize()),
                audio.getDuration(),
                audio.getFileName(),
                audio.getTitle()
        );
    }
}
