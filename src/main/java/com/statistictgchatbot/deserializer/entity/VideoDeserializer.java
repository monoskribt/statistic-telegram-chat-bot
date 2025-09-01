package com.statistictgchatbot.deserializer.entity;

import com.statistictgchatbot.model.submodel.message_model.Video;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
public class VideoDeserializer {

    @ReadingConverter
    public static class StringToVideoConverter implements Converter<String, Video> {
        @Override
        public Video convert(String source) {
            return new Video(null, null, null, source, null);
        }
    }

    @ReadingConverter
    public static class DocumentToVideoConverter implements Converter<Document, Video> {
        @Override
        public Video convert(Document source) {
            String fileId = source.getString("fileId");
            String fileUniqueId = source.getString("fileUniqueId");
            Integer fileSize = source.getInteger("fileSize");
            String fileName = source.getString("fileName");
            Integer duration = source.getInteger("duration");
            return new Video(fileId, fileUniqueId, fileSize, fileName, duration);
        }
    }
}
