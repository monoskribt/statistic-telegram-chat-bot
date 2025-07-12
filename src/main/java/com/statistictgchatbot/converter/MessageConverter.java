package com.statistictgchatbot.converter;

import com.statistictgchatbot.constant.message_entity_constant.MediaType;
import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.converter.service.MediaConverter;
import com.statistictgchatbot.converter.service.MediaConverterRegistry;
import com.statistictgchatbot.model.submodel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.Comparator;
import java.util.Date;

@Component
public class MessageConverter {

    @Autowired
    private MediaConverterRegistry mediaConverterRegistry;

    public Message convertMessageTGEntityToDBEntityForMessagesEvents(
            org.telegram.telegrambots.meta.api.objects.message.Message messageFromTg,
            TypeOfEvent typeOfEvent,
            MediaType mediaType
    ) {
        Message messageToDb = new Message();
        messageToDb.setId(messageFromTg.getMessageId());
        messageToDb.setType(typeOfEvent);
        messageToDb.setCreateAt(new Date());
        messageToDb.setFromUser(messageFromTg.getFrom().getUserName());
        messageToDb.setFromId(messageFromTg.getFrom().getId().toString());
        messageToDb.setMediaType(mediaType);
        messageToDb.setText(messageFromTg.getText());
        messageToDb.setCaption(messageFromTg.getCaption());

        Object telegramMedia = extractTelegramMedia(messageFromTg, mediaType);

        MediaConverter<Object, Object> mediaConverter = mediaConverterRegistry.getConverterByType(mediaType);
        if (mediaConverter != null) {
            try {
                Object converted = mediaConverter.convert(telegramMedia);
                setMediaToMessage(messageToDb, mediaType, converted);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error during converter working " + mediaType + ": " + e.getMessage());
            }
        } else {
            System.out.println("No converter and no fallback for mediaType: " + mediaType);
        }

        return messageToDb;
    }


    private Object extractTelegramMedia(org.telegram.telegrambots.meta.api.objects.message.Message message, MediaType mediaType) {
        return switch (mediaType) {
            case AUDIO -> message.getAudio();
            case VOICE_MESSAGE -> message.getVoice();
            case VIDEO_FILE -> message.getVideo();
            case VIDEO_MESSAGE -> message.getVideoNote();
            case STICKER -> message.getSticker();
            case ANIMATION -> message.getAnimation();
            case PHOTO -> message.getPhoto() != null
                    ? message.getPhoto().stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null)
                    : null;
            default -> null;
        };
    }

    private void setMediaToMessage(Message messageToDb, MediaType mediaType, Object convertedMedia) {
        switch (mediaType) {
            case AUDIO -> messageToDb
                    .setAudio((com.statistictgchatbot.model.submodel.message_model.Audio)
                            convertedMedia);
            case VOICE_MESSAGE -> messageToDb
                    .setVoice((com.statistictgchatbot.model.submodel.message_model.Voice)
                            convertedMedia);
            case VIDEO_FILE -> messageToDb
                    .setVideo((com.statistictgchatbot.model.submodel.message_model.Video)
                            convertedMedia);
            case VIDEO_MESSAGE -> messageToDb
                    .setVideoNote((com.statistictgchatbot.model.submodel.message_model.VideoNote)
                            convertedMedia);
            case ANIMATION -> messageToDb
                    .setAnimation((com.statistictgchatbot.model.submodel.message_model.Animation)
                            convertedMedia);
            case STICKER -> messageToDb
                    .setSticker((com.statistictgchatbot.model.submodel.message_model.Sticker)
                            convertedMedia);
            case PHOTO -> messageToDb
                    .setPhoto((com.statistictgchatbot.model.submodel.message_model.Photo)
                            convertedMedia);
        }
    }
}
