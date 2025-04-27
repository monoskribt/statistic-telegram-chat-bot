package com.statistictgchatbot.dto;

public class WeeklyMessageStatsDTO {

    private int year;
    private int week;
    private int messageCount;

    public WeeklyMessageStatsDTO(int year, int week, int messageCount) {
        this.year = year;
        this.week = week;
        this.messageCount = messageCount;
    }

    public WeeklyMessageStatsDTO() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }
}
