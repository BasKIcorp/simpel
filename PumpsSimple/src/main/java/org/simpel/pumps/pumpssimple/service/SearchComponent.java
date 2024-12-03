package org.simpel.pumps.pumpssimple.service;

import org.simpel.pumps.pumpssimple.model.Point;
import org.simpel.pumps.pumpssimple.model.PointPressure;
import org.simpel.pumps.pumpssimple.model.Pump;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchComponent {
    private float flowRateForSearch;
    private float pressureForSearch;

    public void setFlowRateForSearch(float flowRateForSearch) {
        this.flowRateForSearch = flowRateForSearch;
    }

    public void setPressureForSearch(float pressureForSearch) {
        this.pressureForSearch = pressureForSearch;
    }

    public List<Pump> get(List<Pump> pumps) {
        // Выбираем установки, в которых хотя бы один насос подходит
        List<Pump> resultList = new ArrayList<>();
        for (Pump pump : pumps) {
            if (suitablePumps(pump)) {
                resultList.add(pump);
            }
        }

        // Сортируем по оптимальности (ближе к 60% расхода)
        resultList.sort(Comparator.comparingDouble(pump -> getOptimalityScore(pump, flowRateForSearch)));
        return resultList.stream().limit(7).collect(Collectors.toList());
    }

    private boolean suitablePumps(Pump pump) {
        Point closePoint = findClosePoint(pump.getPointsPressure(), flowRateForSearch);
        if (closePoint != null && pressureForSearch <= closePoint.getY()) {
            return true;
        }

        return false;
    }

    private PointPressure findClosePoint(List<PointPressure> points, float flowRateForSearch) {
        // Находим точку ближайшую к рабочей точке для насоса
        System.out.println("Flow rate for search: " + flowRateForSearch);
        return points.stream()
                .min(Comparator.comparingDouble(point -> Math.abs(point.getX() - flowRateForSearch)))
                .orElse(null);
    }

    private double getOptimalityScore(Pump pump, double flowRate) {
        // Собираем точки давления у переданного насоса
        List<PointPressure> pointsPressure = pump.getPointsPressure();

        // Проверяем, что список точек давления не пуст
        if (pointsPressure.isEmpty()) {
            throw new IllegalArgumentException("Pump has no points pressure data");
        }

        // Находим максимальную точку давления (по оси X)
        double maxFlowRate = pointsPressure.stream()
                .max(Comparator.comparingDouble(PointPressure::getX))
                .map(PointPressure::getX) // Получаем значение X
                .orElseThrow(() -> new IllegalStateException("No max flow rate found"));

        // Возвращаем рассчитанную оптимальность
        return Math.abs(flowRate - 0.65 * maxFlowRate);
    }
}