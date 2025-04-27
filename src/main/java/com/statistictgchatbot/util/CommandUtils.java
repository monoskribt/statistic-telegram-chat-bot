package com.statistictgchatbot.util;

import com.statistictgchatbot.constant.BotCommands;

public class CommandUtils {

    public static String getCommandFromMessage(String messageText) {
        if (messageText.startsWith(BotCommands.MOST_ACTIVE_USERS)) {
            return BotCommands.MOST_ACTIVE_USERS;
        } else if (messageText.startsWith(BotCommands.MOST_INACTIVE_USERS)) {
            return BotCommands.MOST_INACTIVE_USERS;
        } else if (messageText.startsWith(BotCommands.INACTIVE_BY_WEEK)) {
            return BotCommands.INACTIVE_BY_WEEK;
        } else if (messageText.startsWith(BotCommands.AVERAGE_MESSAGE_PER_DAY)) {
            return BotCommands.AVERAGE_MESSAGE_PER_DAY;
        } else if(messageText.startsWith(BotCommands.CHAT_REPORT)) {
            return BotCommands.CHAT_REPORT;
        }else {
            return BotCommands.UNKNOWN_COMMAND;
        }
    }
}
