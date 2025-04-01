package com.statistictgchatbot;

import com.statistictgchatbot.props.BotProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BotProps.class)
public class StatisticTgChatBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticTgChatBotApplication.class, args);
	}

}
