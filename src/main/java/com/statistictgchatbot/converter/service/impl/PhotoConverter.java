package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.message_model.Photo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

@Component
public class PhotoConverter implements MediaConverter<PhotoSize, Photo> {

    @Override
    public Photo convert(PhotoSize photo) {
        return new Photo(
                photo.getFileId(),
                photo.getFileUniqueId(),
                photo.getFileSize(),
                photo.getFilePath()
        );
    }
}
