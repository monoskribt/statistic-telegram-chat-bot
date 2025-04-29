package com.statistictgchatbot.dto;

public class MessageStatsDTO {
    private Integer totalMessage;
    private Integer averageMessagesPerDay;

    public MessageStatsDTO(Integer totalMessage, Integer averageMessagesPerDay) {
        this.totalMessage = totalMessage;
        this.averageMessagesPerDay = averageMessagesPerDay;
    }

    public MessageStatsDTO() {
    }

    public Integer getTotalMessage() {
        return totalMessage;
    }

    public void setTotalMessage(Integer totalMessage) {
        this.totalMessage = totalMessage;
    }

    public Integer getAverageMessagesPerDay() {
        return averageMessagesPerDay;
    }

    public void setAverageMessagesPerDay(Integer averageMessagesPerDay) {
        this.averageMessagesPerDay = averageMessagesPerDay;
    }
}
