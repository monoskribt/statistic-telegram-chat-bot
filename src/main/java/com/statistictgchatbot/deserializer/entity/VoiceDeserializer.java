package com.statistictgchatbot.deserializer.entity;

import com.statistictgchatbot.model.submodel.message_model.Voice;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
public class VoiceDeserializer {

    @ReadingConverter
    public static class StringToVoiceConverter implements Converter<String, Voice> {
        @Override
        public Voice convert(String source) {
            return new Voice(null, null, null, null);
        }
    }

    @ReadingConverter
    public static class DocumentToVoiceConverter implements Converter<Document, Voice> {
        @Override
        public Voice convert(Document source) {
            String fileId = source.getString("fileId");
            String fileUniqueId = source.getString("fileUniqueId");
            Integer fileSize = source.getInteger("fileSize");
            Integer duration = source.getInteger("duration");
            return new Voice(fileId, fileUniqueId, fileSize, duration);
        }
    }
}
