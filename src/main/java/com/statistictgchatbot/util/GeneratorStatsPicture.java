package com.statistictgchatbot.util;

import com.statistictgchatbot.dto.WeeklyMessageStatsDTO;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorStatsPicture {

    public static void buildChart(List<WeeklyMessageStatsDTO> stats, String outputPath) throws IOException {
        List<String> weekLabels = stats.stream()
                .map(s -> s.getYear() + " W" + s.getWeek())
                .collect(Collectors.toList());

        List<Integer> messageCounts = stats.stream()
                .map(WeeklyMessageStatsDTO::getMessageCount)
                .collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(1200).height(600)
                .title("Message statistics by week")
                .xAxisTitle("Year/Week")
                .yAxisTitle("Count of messages")
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setChartBackgroundColor(java.awt.Color.WHITE);
        chart.getStyler().setSeriesColors(new java.awt.Color[]{java.awt.Color.BLUE});
        chart.getStyler().setAxisTickMarkLength(10);
        chart.getStyler().setAxisTickPadding(10);

        chart.addSeries("Messages", weekLabels, messageCounts);

        BitmapEncoder.saveBitmap(chart, outputPath, BitmapEncoder.BitmapFormat.PNG);
    }
}
