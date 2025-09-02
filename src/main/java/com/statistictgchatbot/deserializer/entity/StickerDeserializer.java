package com.statistictgchatbot.deserializer.entity;

import com.statistictgchatbot.model.submodel.message_model.Sticker;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
public class StickerDeserializer {

    @ReadingConverter
    public static class StringToStickerConverter implements Converter<String, Sticker> {
        @Override
        public Sticker convert(String source) {
            return new Sticker(null, null, null, null, source, null);
        }
    }

    @ReadingConverter
    public static class DocumentToStickerConverter implements Converter<Document, Sticker> {
        @Override
        public Sticker convert(Document source) {
            String fileId = source.getString("fileId");
            String fileUniqueId = source.getString("fileUniqueId");
            Integer fileSize = source.getInteger("fileSize");
            String customEmojiId = source.getString("customEmojiId");
            String emoji = source.getString("emoji");
            String type = source.getString("type");
            return new Sticker(fileId, fileUniqueId, fileSize, customEmojiId, emoji, type);
        }
    }
}
