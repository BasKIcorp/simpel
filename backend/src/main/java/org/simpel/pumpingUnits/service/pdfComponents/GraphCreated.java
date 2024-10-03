package org.simpel.pumpingUnits.service.pdfComponents;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;

import org.jfree.data.xy.XYSeriesCollection;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.installation.*;
import org.simpel.pumpingUnits.model.installation.Point;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;



public class GraphCreated {
    public static byte[] createGraph(List<? extends  Point> points, String type, int countPumps) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int i = 0; i < countPumps; i++) {
            XYSeries series = new XYSeries("Data points"+(i+1));
            for (Point point : points) {
                series.add(point.getX()*Math.pow(2,i), point.getY());
            }
            dataset.addSeries(series);
        }
        JFreeChart chart = ChartFactory.createXYLineChart(
                "График flowRate/"+type,
                "flowRate",
                type,
                dataset);
        chart.setBackgroundPaint(Color.WHITE);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        xAxis.setLabel("flowRate");
        yAxis.setLabel(type);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, new Color((int)(Math.random() * 0x1000000))); // Случайные цвета для каждой серии
        }
        plot.setRenderer(renderer);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsPNG(baos, chart, 250, 250);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
