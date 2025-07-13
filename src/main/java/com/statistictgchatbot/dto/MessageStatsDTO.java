package com.statistictgchatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MessageStatsDTO {

    private Integer totalMessage;
    private Integer averageMessagesPerDay;
}
