package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.message_model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoConverter implements MediaConverter<org.telegram.telegrambots.meta.api.objects.Video, Video> {

    @Override
    public com.statistictgchatbot.model.submodel.message_model.Video convert(org.telegram.telegrambots.meta.api.objects.Video video) {
        return new com.statistictgchatbot.model.submodel.message_model.Video(
                video.getFileId(),
                video.getFileUniqueId(),
                Math.toIntExact(video.getFileSize()),
                video.getFileName(),
                video.getDuration()
        );
    }
}
