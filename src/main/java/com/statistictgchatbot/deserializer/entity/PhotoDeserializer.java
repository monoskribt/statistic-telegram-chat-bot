package com.statistictgchatbot.deserializer.entity;

import com.statistictgchatbot.model.submodel.message_model.Photo;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
public class PhotoDeserializer {

    @ReadingConverter
    public static class StringToPhotoConverter implements Converter<String, Photo> {
        @Override
        public Photo convert(@NotNull String source) {
            return new Photo(null, null, null, source);
        }
    }

    @ReadingConverter
    public static class DocumentToPhotoConverter implements Converter<Document, Photo> {
        @Override
        public Photo convert(Document source) {
            String fileId = source.getString("fileId");
            String fileUniqueId = source.getString("fileUniqueId");
            Integer fileSize = source.getInteger("fileSize");
            String filePath = source.getString("filePath");
            return new Photo(fileId, fileUniqueId, fileSize, filePath);
        }
    }
}
