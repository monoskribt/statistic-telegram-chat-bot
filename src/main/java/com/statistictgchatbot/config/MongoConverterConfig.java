package com.statistictgchatbot.config;

import com.statistictgchatbot.deserializer.entity.*;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MongoConverterConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new PhotoDeserializer.StringToPhotoConverter(),
                new PhotoDeserializer.DocumentToPhotoConverter(),

                new AudioDeserializer.StringToAudioConverter(),
                new AudioDeserializer.DocumentToAudioConverter(),

                new VoiceDeserializer.StringToVoiceConverter(),
                new VoiceDeserializer.DocumentToVoiceConverter(),

                new VideoDeserializer.StringToVideoConverter(),
                new VideoDeserializer.DocumentToVideoConverter(),

                new VideoNoteDeserializer.StringToVideoNoteConverter(),
                new VideoNoteDeserializer.DocumentToVideoNoteConverter(),

                new AnimationDeserializer.StringToAnimationConverter(),
                new AnimationDeserializer.DocumentToAnimationConverter(),

                new StickerDeserializer.StringToStickerConverter(),
                new StickerDeserializer.DocumentToStickerConverter()
        ));
    }
}
