package com.statistictgchatbot.dto;

import java.util.Date;

public class UserMessageStatsDTO {
    private String username;
    private int messageCount;
    private Date lastMessageTime;

    public UserMessageStatsDTO(String username, int countOfMessage) {
        this.username = username;
        this.messageCount = countOfMessage;
    }

    public UserMessageStatsDTO(String username, Date lastMessageTime) {
        this.username = username;
        this.lastMessageTime = lastMessageTime;
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

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
