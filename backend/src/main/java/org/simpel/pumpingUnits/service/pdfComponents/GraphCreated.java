package org.simpel.pumpingUnits.service.pdfComponents;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.XYSeries;

import org.jfree.data.xy.XYSeriesCollection;
import org.simpel.pumpingUnits.model.installation.Point;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;



public class GraphCreated {
    public static byte[] createGraph(List<? extends  Point> points, String type, int countPumps, float x, float y) {
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
        chart.setBackgroundPaint(Color.white);

        TextTitle title = new TextTitle("График flowRate/" + type, new Font("Times New Roman", Font.BOLD, 18));
        title.setHorizontalAlignment(HorizontalAlignment.RIGHT);  // Выравнивание заголовка
        chart.setTitle(title);
        LegendTitle legendTitle = chart.getLegend();
        legendTitle.setPosition(RectangleEdge.BOTTOM);
        legendTitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        legendTitle.setItemFont(new Font("Times New Roman", Font.PLAIN, 10));

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(Color.WHITE);

        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

        xAxis.setLabelFont(new Font("Times New Roman", Font.PLAIN, 12));
        xAxis.setTickLabelFont(new Font("Times New Roman", Font.PLAIN, 10));

        yAxis.setLabelFont(new Font("Times New Roman", Font.PLAIN, 12));
        yAxis.setTickLabelFont(new Font("Times New Roman", Font.PLAIN, 10));


        xAxis.setLabel("flowRate");
        yAxis.setLabel(type);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, new Color((int)(Math.random() * 0x1000000))); // Случайные цвета для каждой серии
            renderer.setSeriesLinesVisible(i, true);  // Включить линии для каждой серии
            renderer.setSeriesShapesVisible(i, false);  // Отключить точки для каждой серии
            renderer.setSeriesStroke(i, new BasicStroke(2.0f));
        }
        XYSeries workPointSeries = new XYSeries("раб. точка");
        workPointSeries.add(x, y);
        dataset.addSeries(workPointSeries);
        int workPointIndex = dataset.getSeriesIndex("раб. точка");
        renderer.setSeriesShape(workPointIndex, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));

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
