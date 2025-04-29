package com.statistictgchatbot.constant;

public class MessageToUser {
    public static final String SUCCESSFULLY_SAVED = "Your document is successfully saved";
    public static final String SUCCESSFULLY_UPDATED = "Your document is successfully updated on new";
    public static final String ALREADY_EXISTS = "Your document is already exists";
    public static final String CHAT_WAS_NOT_SAVED =
            "Your document was not saved because of your statistic is already present!";
    public static final String UNKNOWN_COMMAND =
            "You have entered an unknown command. This message is intended for information on entering commands in the bot.\n" +
                    "/activeusers {your chat name} - shows the most active users in the chat\n" +
                    "/inactiveusers {your chat name} - shows the most inactive users in the chat\n" +
                    "/inactivebyweek {your chat name} - shows users who have not done any actions in the chat for a week\n" +
                    "/avgmessages {your chat name} - shows the average number of messages per month for the chat\n" +
                    "/chatreport {your chat name} - shows a report based on statistics from the selected chat";
    public static final String CHAT_DOES_NOT_FOUND = "Chat with your title does not exists";
}
