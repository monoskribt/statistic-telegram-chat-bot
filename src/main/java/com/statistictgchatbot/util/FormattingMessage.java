package com.statistictgchatbot.util;

import com.statistictgchatbot.constant.message_entity_constant.TypeOfEvent;
import com.statistictgchatbot.dto.UserMessageStatsDTO;
import com.statistictgchatbot.dto.WeeklyMessageStatsDTO;

import java.util.List;
import java.util.Map;

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

    public static String formatMostActiveUsersMessageWithUsersFlow(
            List<UserMessageStatsDTO> userStatistic,
            Map<TypeOfEvent, Integer> joinedLeftStatistic) {
        StringBuilder sb = new StringBuilder(formatMostActiveUsersMessage(userStatistic));

        sb.append("\n📊 User flow statistics:\n\n");

        int joined = joinedLeftStatistic.getOrDefault(TypeOfEvent.JOIN_MEMBER, 0);
        int left = joinedLeftStatistic.getOrDefault(TypeOfEvent.LEAVE_MEMBER, 0);
        int net = joined - left;

        sb.append(String.format("👥 Users joined: %d\n", joined));
        sb.append(String.format("🚪 Users left: %d\n", left));
        sb.append(String.format("📈 Net change: %s%d\n\n", net >= 0 ? "+" : "", net));

        if (net > 0) {
            sb.append("✅ More users joined than left - great engagement this week!\n");
        } else if (net < 0) {
            sb.append("⚠️ More users left than joined - consider checking in with the community.\n");
        } else {
            sb.append("➖ No net change in users - stable week.\n");
        }

        return sb.toString();
    }
}
