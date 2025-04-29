package com.statistictgchatbot.util;

import static com.statistictgchatbot.constant.BotCommands.*;

public class CommandUtils {

    public static String getCommandFromMessage(String messageText) {
        if (messageText.startsWith(MOST_ACTIVE_USERS)) {
            return MOST_ACTIVE_USERS;
        } else if (messageText.startsWith(MOST_INACTIVE_USERS)) {
            return MOST_INACTIVE_USERS;
        } else if (messageText.startsWith(INACTIVE_BY_WEEK)) {
            return INACTIVE_BY_WEEK;
        } else if (messageText.startsWith(AVERAGE_MESSAGE_PER_DAY)) {
            return AVERAGE_MESSAGE_PER_DAY;
        } else if(messageText.startsWith(CHAT_REPORT)) {
            return CHAT_REPORT;
        }else {
            return UNKNOWN_COMMAND;
        }
    }
}
