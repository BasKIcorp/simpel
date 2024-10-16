package org.simpel.pumpingUnits.service.pdfComponents;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.XYSeries;

import org.jfree.data.xy.XYSeriesCollection;
import org.simpel.pumpingUnits.model.installation.Point;
import org.simpel.pumpingUnits.model.installation.PointNPSH;
import org.simpel.pumpingUnits.model.installation.PointPower;
import org.simpel.pumpingUnits.model.installation.PointPressure;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.awt.Color;


public class GraphCreated {
    public static byte[] createGraph(List<? extends Point> points, String type, int countPumps, float x, float y, boolean needTitle, boolean needLegend) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        Color[] pumpColors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA};

        for (int i = 0; i < countPumps; i++) {
            XYSeries series = new XYSeries("Насос " + (i + 1));
            for (Point point : points) {
                series.add(point.getX() * Math.pow(2, i), point.getY());
            }
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                needTitle ? "Гидравлические характеристики" : "",
                "flowRate",
                type,
                dataset
        );
        TextTitle title = new TextTitle(needTitle ? "Гидравлические характеристики" : "", new Font("Times New Roman", Font.BOLD, 16));
        title.setHorizontalAlignment(HorizontalAlignment.RIGHT);  // Выравнивание заголовка
        chart.setTitle(title);
        LegendTitle legendTitle = chart.getLegend();
        legendTitle.setPosition(RectangleEdge.BOTTOM);
        legendTitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        legendTitle.setItemFont(new Font("Times New Roman", Font.PLAIN, 10));
        if (!needLegend) {
            chart.removeLegend();
        }

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

        xAxis.setLabelFont(new Font("Times New Roman", Font.PLAIN, 12));
        xAxis.setTickLabelFont(new Font("Times New Roman", Font.PLAIN, 10));
        xAxis.setLabel("flowRate");

        yAxis.setLabelFont(new Font("Times New Roman", Font.PLAIN, 12));
        yAxis.setTickLabelFont(new Font("Times New Roman", Font.PLAIN, 10));
        yAxis.setLabel(type);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, pumpColors[i % pumpColors.length]);
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, false);
            renderer.setSeriesStroke(i, new BasicStroke(2.0f));
        }

        // Добавление рабочей точки
        XYSeries workPointSeries = new XYSeries("раб. точка");
        workPointSeries.add(x, y);
        dataset.addSeries(workPointSeries);
        int workPointIndex = dataset.getSeriesIndex("раб. точка");
        renderer.setSeriesPaint(workPointIndex, Color.BLACK);
        renderer.setSeriesShape(workPointIndex, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));
        renderer.setSeriesShapesVisible(workPointIndex, true);
        renderer.setSeriesLinesVisible(workPointIndex, false);

        plot.setRenderer(renderer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsPNG(baos, chart, 250, 250);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static byte[] createCombinedGraph(List<? extends Point> pointsPressure, List<? extends Point> pointsPower, List<? extends Point> pointsNPSH,
                                             int countPumps, float x, float y) {

        Color[] pumpColors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA};
        CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(new NumberAxis("flowRate")); // Общая ось X

        // Типы графиков
        String[] types = {"Pressure", "Power", "NPSH"};
        List<? extends List<? extends Point>> dataPoints = List.of(pointsPressure, pointsPower, pointsNPSH);

        for (int graphIndex = 0; graphIndex < 3; graphIndex++) {
            XYSeriesCollection dataset = new XYSeriesCollection();

            // Добавляем данные насосов
            for (int i = 0; i < countPumps; i++) {
                XYSeries series = new XYSeries("Насос " + (i + 1));
                for (Point point : dataPoints.get(graphIndex)) {
                    series.add(point.getX() * Math.pow(2, i), point.getY());
                }
                dataset.addSeries(series);
            }

            // Добавляем рабочую точку
            if (graphIndex == 0) {
                XYSeries workPointSeries = new XYSeries("раб. точка");
                workPointSeries.add(x, y);
                dataset.addSeries(workPointSeries);
            }

            // Настройка отрисовщика
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            for (int i = 0; i < dataset.getSeriesCount(); i++) {
                if (i < countPumps) {
                    renderer.setSeriesPaint(i, pumpColors[i % pumpColors.length]);
                    renderer.setSeriesLinesVisible(i, true);
                    renderer.setSeriesShapesVisible(i, false);
                    renderer.setSeriesStroke(i, new BasicStroke(2.0f));
                } else if (i == countPumps && graphIndex == 0) {
                    // Настройка рабочей точки
                    renderer.setSeriesPaint(i, Color.BLACK);
                    renderer.setSeriesShape(i, new Ellipse2D.Double(-3, -3, 6, 6));
                    renderer.setSeriesShapesVisible(i, true);
                    renderer.setSeriesLinesVisible(i, false);
                }
            }

//            // Настройка рабочей точки
//                int workPointIndex = dataset.getSeriesIndex("раб. точка");
//                renderer.setSeriesPaint(workPointIndex, Color.BLACK);
//                renderer.setSeriesShape(workPointIndex, new Ellipse2D.Double(-3, -3, 6, 6));
//                renderer.setSeriesShapesVisible(workPointIndex, true);
//                renderer.setSeriesLinesVisible(workPointIndex, false);

            // Создаем ось Y для каждого графика
            NumberAxis yAxis = new NumberAxis(types[graphIndex]);
            yAxis.setAutoRangeIncludesZero(true); // Автоматический диапазон, включая ноль

            // Создаем график
            XYPlot subplot = new XYPlot(dataset, null, yAxis, renderer);
            subplot.setBackgroundPaint(Color.WHITE);
            subplot.setDomainGridlinePaint(Color.GRAY);
            subplot.setRangeGridlinePaint(Color.GRAY);

            combinedPlot.add(subplot); // Добавляем график в комбинированное полотно
        }

        // Создаем основной график
        JFreeChart combinedChart = new JFreeChart("Гидравлические характеристики",
                JFreeChart.DEFAULT_TITLE_FONT,
                combinedPlot,
                false); // Отключаем автоматическую легенду

        // Настраиваем легенду
        LegendItemCollection legendItems = new LegendItemCollection();
        for (int i = 0; i < countPumps; i++) {
            LegendItem legendItem = new LegendItem("Насос " + (i + 1), pumpColors[i % pumpColors.length]);
            legendItems.add(legendItem);
        }

        // Добавляем рабочую точку
        LegendItem workPointLegendItem = new LegendItem("раб. точка", Color.BLACK);
        workPointLegendItem.setShape(new Ellipse2D.Double(-3, -3, 6, 6));
        workPointLegendItem.setFillPaint(Color.BLACK);
        legendItems.add(workPointLegendItem);

        // Устанавливаем кастомную легенду
        combinedPlot.setFixedLegendItems(legendItems);

        // Создаем объект LegendTitle и устанавливаем его внизу
        TextTitle title = new TextTitle("Гидравлические характеристики", new Font("Times New Roman", Font.BOLD, 16));
        title.setHorizontalAlignment(HorizontalAlignment.RIGHT);  // Выравнивание заголовка
        combinedChart.setTitle(title);
        LegendTitle legend = new LegendTitle(combinedPlot);
        legend.setPosition(RectangleEdge.BOTTOM);
        combinedChart.addSubtitle(legend);
        combinedChart.setBackgroundPaint(Color.WHITE);

        // Экспорт в массив байтов
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsPNG(baos, combinedChart, 250, 750); // Настраиваем размер по вашему усмотрению
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}






