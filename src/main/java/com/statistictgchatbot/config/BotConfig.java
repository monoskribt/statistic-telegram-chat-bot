package com.statistictgchatbot.config;

import com.statistictgchatbot.controller.BotController;
import com.statistictgchatbot.props.BotProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.DefaultGetUpdatesGenerator;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class BotConfig {

    private final BotProps botProps;

    public BotConfig(BotProps botProps) {
        this.botProps = botProps;
    }

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(botProps.token());
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication() {
        return new TelegramBotsLongPollingApplication() {
            @Override
            public BotSession registerBot(String botToken, LongPollingUpdateConsumer updatesConsumer) throws TelegramApiException {
                return registerBot(botToken, () -> TelegramUrl.DEFAULT_URL,
                        new DefaultGetUpdatesGenerator(getAllowedUpdates()), updatesConsumer);
            }

            public BotSession registerBot(String token,
                                          Supplier<TelegramUrl> telegramUrlSupplier,
                                          Function<Integer, GetUpdates> getUpdatesGenerator,
                                          LongPollingUpdateConsumer updatesConsumer)
                    throws TelegramApiException {
                return super.registerBot(token, telegramUrlSupplier,
                        new DefaultGetUpdatesGenerator(getAllowedUpdates()), updatesConsumer);
            }
        };
    }

    @Bean
    public BotSession botSession(TelegramBotsLongPollingApplication app, BotController botController) throws TelegramApiException {
        return app.registerBot(botProps.token(), botController);
    }

    private List<String> getAllowedUpdates() {
        return Arrays.asList(
                "message", "edited_message", "channel_post",
                "edited_channel_post", "message_reaction", "message_reaction_count",
                "my_chat_member", "chat_member", "chat_join_request"
        );
    }
}
