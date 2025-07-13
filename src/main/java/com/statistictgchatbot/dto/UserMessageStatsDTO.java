package com.statistictgchatbot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserMessageStatsDTO {

    private String username;
    private int messageCount;
    private Date lastMessageTime;
}
