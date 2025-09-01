package com.statistictgchatbot.deserializer.entity;

import com.statistictgchatbot.model.submodel.message_model.Animation;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
public class AnimationDeserializer {

    @ReadingConverter
    public static class StringToAnimationConverter implements Converter<String, Animation> {
        @Override
        public Animation convert(String source) {
            return new Animation(null, null, null, source);
        }
    }

    @ReadingConverter
    public static class DocumentToAnimationConverter implements Converter<Document, Animation> {
        @Override
        public Animation convert(Document source) {
            String fileId = source.getString("fileId");
            String fileUniqueId = source.getString("fileUniqueId");
            Integer fileSize = source.getInteger("fileSize");
            String mimeType = source.getString("mimeType");
            return new Animation(fileId, fileUniqueId, fileSize, mimeType);
        }
    }
}
