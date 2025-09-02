package com.statistictgchatbot.deserializer.entity;

import com.statistictgchatbot.model.submodel.message_model.VideoNote;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
public class VideoNoteDeserializer {

    @ReadingConverter
    public static class StringToVideoNoteConverter implements Converter<String, VideoNote> {
        @Override
        public VideoNote convert(String source) {
            return new VideoNote(null, null, null, null, null);
        }
    }

    @ReadingConverter
    public static class DocumentToVideoNoteConverter implements Converter<Document, VideoNote> {
        @Override
        public VideoNote convert(Document source) {
            String fileId = source.getString("fileId");
            String fileUniqueId = source.getString("fileUniqueId");
            Integer fileSize = source.getInteger("fileSize");
            Integer duration = source.getInteger("duration");
            Integer length = source.getInteger("length");
            return new VideoNote(fileId, fileUniqueId, fileSize, duration, length);
        }
    }
}
