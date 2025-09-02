package com.statistictgchatbot.deserializer.entity;

import com.statistictgchatbot.model.submodel.message_model.Audio;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
public class AudioDeserializer {

    @ReadingConverter
    public static class StringToAudioConverter implements Converter<String, Audio> {
        @Override
        public Audio convert(String source) {
            return new Audio(null, null, null, null, null, source);
        }
    }

    @ReadingConverter
    public static class DocumentToAudioConverter implements Converter<Document, Audio> {
        @Override
        public Audio convert(Document source) {
            String fileId = source.getString("fileId");
            String fileUniqueId = source.getString("fileUniqueId");
            Integer fileSize = source.getInteger("fileSize");
            Integer duration = source.getInteger("duration");
            String fileName = source.getString("fileName");
            String title = source.getString("title");
            return new Audio(fileId, fileUniqueId, fileSize, duration, fileName, title);
        }
    }
}
