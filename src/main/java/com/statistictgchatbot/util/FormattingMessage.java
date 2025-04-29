package com.statistictgchatbot.util;

import com.statistictgchatbot.dto.WeeklyMessageStatsDTO;

public class FormattingMessage {

    public static String formatReportMessage(int totalMessages,
                                             double averageMessages,
                                             WeeklyMessageStatsDTO mostActiveWeek) {
        return String.format(
                """
                📊 Messages report:
    
                • Total messages: %d
                • Average messages per week: %.2f
                • Most active week: %d year, %d week (%d messages)
                """,
                totalMessages,
                averageMessages,
                mostActiveWeek != null ? mostActiveWeek.getYear() : 0,
                mostActiveWeek != null ? mostActiveWeek.getWeek() : 0,
                mostActiveWeek != null ? mostActiveWeek.getMessageCount() : 0
        );
    }
}
