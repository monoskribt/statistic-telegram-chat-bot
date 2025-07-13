package com.statistictgchatbot.converter.service;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.converter.service.impl.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MediaConverterRegistry {
    private final Map<MediaType, MediaConverter<?, ?>> mapRegistryConverters = new HashMap<>();

    public MediaConverterRegistry(List<MediaConverter<?, ?>> converters) {
        converters.forEach(converter -> {
            if(converter instanceof PhotoConverter) {
                mapRegistryConverters.put(MediaType.PHOTO, converter);
            }
            else if(converter instanceof AudioConverter) {
                mapRegistryConverters.put(MediaType.AUDIO, converter);
            }
            else if(converter instanceof VoiceConverter) {
                mapRegistryConverters.put(MediaType.VOICE_MESSAGE, converter);
            }
            else if(converter instanceof VideoConverter) {
                mapRegistryConverters.put(MediaType.VIDEO_FILE, converter);
            }
            else if(converter instanceof VideoNoteConverter) {
                mapRegistryConverters.put(MediaType.VIDEO_MESSAGE, converter);
            }
            else if(converter instanceof AnimationConverter) {
                mapRegistryConverters.put(MediaType.ANIMATION, converter);
            }
            else if(converter instanceof StickerConverter) {
                mapRegistryConverters.put(MediaType.STICKER, converter);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <S, T> MediaConverter<S, T> getConverterByType(MediaType mediaType) {
        MediaConverter<?, ?> converter = mapRegistryConverters.get(mediaType);
        return (MediaConverter<S, T>) converter;
    }
}
