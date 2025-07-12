package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.message_model.Sticker;
import org.springframework.stereotype.Component;

@Component
public class StickerConverter implements MediaConverter<org.telegram.telegrambots.meta.api.objects.stickers.Sticker, Sticker> {

    @Override
    public com.statistictgchatbot.model.submodel.message_model.Sticker convert(org.telegram.telegrambots.meta.api.objects.stickers.Sticker sticker) {
        return new com.statistictgchatbot.model.submodel.message_model.Sticker(
                sticker.getFileId(),
                sticker.getFileUniqueId(),
                sticker.getFileSize(),
                sticker.getCustomEmojiId(),
                sticker.getEmoji(),
                sticker.getType()
        );
    }
}
