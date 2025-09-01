package com.statistictgchatbot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class UserMessageStatsDTO {

    private String username;
    private int messageCount;
    private Date lastMessageTime;
}
