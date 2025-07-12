package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.message_model.VideoNote;
import org.springframework.stereotype.Component;

@Component
public class VideoNoteConverter implements MediaConverter<org.telegram.telegrambots.meta.api.objects.VideoNote, VideoNote> {

    @Override
    public com.statistictgchatbot.model.submodel.message_model.VideoNote convert(org.telegram.telegrambots.meta.api.objects.VideoNote videoNote) {
        return new com.statistictgchatbot.model.submodel.message_model.VideoNote(
                videoNote.getFileId(),
                videoNote.getFileUniqueId(),
                videoNote.getFileSize(),
                videoNote.getDuration(),
                videoNote.getLength()
        );
    }
}
