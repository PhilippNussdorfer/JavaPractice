package bbrz.at;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LineChart extends JPanel {

    private final int padding = 35;
    private final int labelPadding = 25;
    private Color lineColor = Color.DARK_GRAY;
    private Color pointColor = Color.gray;
    private final Color gridColor = Color.BLACK;
    private static final Stroke GRAPH_STROKE = new BasicStroke(2);
    private final int pointWidth = 4;
    private final int numberYDivisions = 10;
    private List<Double> temperatures;
    private List<String> dates;

    public LineChart(List<Double> temperatures, List<String> dates) {
        this.temperatures = temperatures;
        this.dates = dates;
    }

    public void setLineColor(Color color) {
        this.lineColor = color;
    }

    public void setPointColor(Color color) {
        this.pointColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (temperatures.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxTemperature() - getMinTemperature());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < temperatures.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxTemperature() - temperatures.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2D.setColor(Color.WHITE);
        g2D.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2D.setColor(Color.BLACK);

        // create hatch marks and grid lines for y-axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (temperatures.size() > 0) {
                g2D.setColor(gridColor);
                g2D.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2D.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinTemperature() + (getMaxTemperature() - getMinTemperature()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2D.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2D.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2D.drawLine(x0, y0, x1, y1);
        }

        // and for x-axis
        for (int i = 0; i < this.dates.size(); i++) {
            if (temperatures.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (temperatures.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((temperatures.size() / 20.0)) + 1)) == 0) {
                    g2D.setColor(gridColor);
                    g2D.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2D.setColor(Color.BLACK);
                    String xLabel = this.dates.get(i);//i + "";
                    FontMetrics metrics = g2D.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2D.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2D.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2D.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2D.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2D.getStroke();
        g2D.setColor(lineColor);
        g2D.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2D.drawLine(x1, y1, x2, y2);
        }

        g2D.setStroke(oldStroke);
        g2D.setColor(pointColor);
        for (Point graphPoint : graphPoints) {
            int x = graphPoint.x - pointWidth / 2;
            int y = graphPoint.y - pointWidth / 2;
            g2D.fillOval(x, y, pointWidth, pointWidth);
        }
    }

    private double getMaxTemperature() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : this.temperatures) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    private double getMinTemperature() {
        double minScore = Double.MAX_VALUE;
        for (Double score : this.temperatures) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }
}
