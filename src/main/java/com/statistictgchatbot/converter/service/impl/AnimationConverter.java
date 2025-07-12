package com.statistictgchatbot.converter.service.impl;

import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.model.submodel.message_model.Animation;
import org.springframework.stereotype.Component;

@Component
public class AnimationConverter implements MediaConverter<org.telegram.telegrambots.meta.api.objects.games.Animation, Animation> {

    @Override
    public com.statistictgchatbot.model.submodel.message_model.Animation convert(org.telegram.telegrambots.meta.api.objects.games.Animation animation) {
        return new com.statistictgchatbot.model.submodel.message_model.Animation(
                animation.getFileId(),
                animation.getFileUniqueId(),
                Math.toIntExact(animation.getFileSize()),
                animation.getMimeType()
        );
    }
}
