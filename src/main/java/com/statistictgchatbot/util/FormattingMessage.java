package com.statistictgchatbot.util;

import com.statistictgchatbot.dto.UserMessageStatsDTO;
import com.statistictgchatbot.dto.WeeklyMessageStatsDTO;

import java.util.List;

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

    public static String formatMostActiveUsersMessage(List<UserMessageStatsDTO> userStatistic) {
        StringBuilder sb = new StringBuilder("\uD83D\uDD25 TOP-3 active participants of the week: \n\n");

        for(int i = 0; i < userStatistic.size(); i++) {
            UserMessageStatsDTO stat = userStatistic.get(i);
            String medal = switch (i) {
                case 0 -> "🥇";
                case 1 -> "🥈";
                case 2 -> "🥉";
                default -> "⭐";
            };
            sb.append("%s %s - %d messages \n".formatted(medal, stat.getUsername(), stat.getMessageCount()));
        }

        return sb.toString();
    }
}
