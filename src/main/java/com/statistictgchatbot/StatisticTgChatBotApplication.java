package com.statistictgchatbot;

import com.statistictgchatbot.props.BotProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(BotProps.class)
@EnableScheduling
public class StatisticTgChatBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticTgChatBotApplication.class, args);
	}

}
