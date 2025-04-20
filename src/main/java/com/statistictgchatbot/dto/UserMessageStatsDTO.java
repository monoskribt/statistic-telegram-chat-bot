package com.statistictgchatbot.dto;

public class UserMessageStatsDTO {
    private String username;
    private int messageCount;

    public UserMessageStatsDTO(String username, int countOfMessage) {
        this.username = username;
        this.messageCount = countOfMessage;
    }

    public UserMessageStatsDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
}
