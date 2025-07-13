package com.statistictgchatbot.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeeklyMessageStatsDTO {

    private Integer year;
    private Integer week;
    private Integer messageCount;
}
