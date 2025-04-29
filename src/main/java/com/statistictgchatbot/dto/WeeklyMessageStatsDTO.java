package com.statistictgchatbot.dto;


public class WeeklyMessageStatsDTO {

    private Integer year;
    private Integer week;
    private Integer messageCount;

    public WeeklyMessageStatsDTO(Integer year, Integer week, Integer messageCount) {
        this.year = year;
        this.week = week;
        this.messageCount = messageCount;
    }

    public WeeklyMessageStatsDTO() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }
}
