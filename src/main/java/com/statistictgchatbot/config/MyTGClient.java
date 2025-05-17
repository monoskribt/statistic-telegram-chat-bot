package com.statistictgchatbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistictgchatbot.props.BotProps;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.stickers.*;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class MyTGClient implements TelegramClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BotProps botProps;
    private final OkHttpClient okHttpClient = new OkHttpClient();

    public MyTGClient(BotProps botProps) {
        this.botProps = botProps;
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        return null;
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        try {
            String url = "https://api.telegram.org/bot" + botProps.token() + "/" + method.getMethod();
            String jsonMethod = objectMapper.writeValueAsString(method);
            RequestBody body = RequestBody.create(jsonMethod, MediaType.get("application/json"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new TelegramApiException("Request failed: " + response.message());
            }
            String json = response.body().string();
            return method.deserializeResponse(json);
        } catch (TelegramApiException e) {
            throw e;
        } catch (Exception e) {
            throw new TelegramApiException(e);
        }
    }

    @Override
    public Message execute(SendDocument sendDocument) throws TelegramApiException {
        throw new UnsupportedOperationException("SendDocument is not implemented yet");
    }

    @Override
    public Message execute(SendPhoto sendPhoto) throws TelegramApiException {
        return execute(sendPhoto);
    }

    @Override
    public Boolean execute(SetWebhook setWebhook) throws TelegramApiException {
        return null;
    }

    @Override
    public Message execute(SendVideo sendVideo) throws TelegramApiException {
        return null;
    }

    @Override
    public Message execute(SendVideoNote sendVideoNote) throws TelegramApiException {
        return null;
    }

    @Override
    public Message execute(SendSticker sendSticker) throws TelegramApiException {
        return null;
    }

    @Override
    public Message execute(SendAudio sendAudio) throws TelegramApiException {
        return null;
    }

    @Override
    public Message execute(SendVoice sendVoice) throws TelegramApiException {
        return null;
    }

    @Override
    public List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException {
        return List.of();
    }

    @Override
    public List<Message> execute(SendPaidMedia sendPaidMedia) throws TelegramApiException {
        return List.of();
    }

    @Override
    public Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException {
        return null;
    }

    @Override
    public Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException {
        return null;
    }

    @Override
    public Boolean execute(ReplaceStickerInSet replaceStickerInSet) throws TelegramApiException {
        return null;
    }

    @Override
    public Boolean execute(SetStickerSetThumbnail setStickerSetThumbnail) throws TelegramApiException {
        return null;
    }

    @Override
    public Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        return null;
    }

    @Override
    public File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        return null;
    }

    @Override
    public Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException {
        return null;
    }

    @Override
    public java.io.File downloadFile(File file) throws TelegramApiException {
        return null;
    }

    @Override
    public InputStream downloadFileAsStream(File file) throws TelegramApiException {
        return null;
    }

    @Override
    public Message execute(SendAnimation sendAnimation) throws TelegramApiException {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetWebhook setWebhook) {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAudio sendAudio) {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVoice sendVoice) {
        return null;
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        return null;
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendPaidMedia sendPaidMedia) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(AddStickerToSet addStickerToSet) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(ReplaceStickerInSet replaceStickerInSet) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumbnail setStickerSetThumbnail) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        return null;
    }

    @Override
    public CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile) {
        return null;
    }

    @Override
    public CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia) {
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAnimation sendAnimation) {
        return null;
    }

    @Override
    public CompletableFuture<java.io.File> downloadFileAsync(File file) {
        return null;
    }

    @Override
    public CompletableFuture<InputStream> downloadFileAsStreamAsync(File file) {
        return null;
    }
}
