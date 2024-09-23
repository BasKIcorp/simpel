package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.model.installation.Point;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.installation.PointPressure;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchComponent<T extends ParentInstallations> {
    private float flowRateForSearch;
    private float pressureForSearch;

    public void setFlowRateForSearch(float flowRateForSearch) {
        this.flowRateForSearch = flowRateForSearch;
    }

    public void setPressureForSearch(float pressureForSearch) {
        this.pressureForSearch = pressureForSearch;
    }

    public List<T> get(List<T> installations) {
        // Выбираем установки, в которых хотя бы один насос подходит
        List<T> resultList = new ArrayList<>();
        for (T installation : installations) {
            System.out.println(installation.getPumps());
            if (suitableInstallation(installation)) {
                resultList.add(installation);
            }
        }

        // Сортируем по оптимальности (ближе к 60% расхода)
        resultList.sort(Comparator.comparingDouble(pump -> getOptimalityScore(pump, flowRateForSearch)));
        return resultList.stream().limit(7).collect(Collectors.toList());
    }

    private boolean suitableInstallation(T installation) {
        // Проверяем каждый насос в установке
        for (Pump pump : installation.getPumps()) {
            Point closePoint = findClosePoint(pump.getPointsPressure(), flowRateForSearch);
            if (closePoint != null && pressureForSearch <= closePoint.getY()) {
                return true; // Если хотя бы один насос подходит, установка считается подходящей
            }
        }
        return false;
    }

    private PointPressure findClosePoint(List<PointPressure> points, float flowRateForSearch) {
        // Находим точку ближайшую к рабочей точке для насоса
        points.forEach(point -> System.out.println("Point x: " + point.getX()));
        System.out.println("Flow rate for search: " + flowRateForSearch);
        return points.stream()
                .min(Comparator.comparingDouble(point -> Math.abs(point.getX() - flowRateForSearch)))
                .orElse(null);
    }

    private double getOptimalityScore(T installation, double flowRate) {
        // Считаем оптимальность для установки (на основе оптимальных диапазонов расхода)
        List<PointPressure> allPointsPressure = (List<PointPressure>) installation.getPumps().stream()
                .flatMap(pump -> pump.getPointsPressure().stream())
                .collect(Collectors.toList()); // Собираем все точки давления в один список

        // Находим максимальную точку давления
        double maxFlowRate = allPointsPressure.stream()
                .max(Comparator.comparingDouble(PointPressure::getX))
                .map(PointPressure::getX) // Получаем значение X
                .orElseThrow(() -> new NullPointerException("No points pressure found")); // Обработка отсутствия значений

        return Math.abs(flowRate - 0.65 * maxFlowRate);
    }

    public int getMaxFlowRate() {
        return (int) Math.ceil(((100 * flowRateForSearch) / 60) * 0.25);
    }

    public int getMinFlowRate() {
        return (int) Math.floor(((100 * flowRateForSearch) / 60) * 0.85);
    }
}
