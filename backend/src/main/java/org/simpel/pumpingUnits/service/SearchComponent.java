package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchComponent <T extends ParentInstallations>  {
    private float flowRateForSearch;
    private float pressureForSearch;

    public void setFlowRateForSearch(float flowRateForSearch) {
        this.flowRateForSearch = flowRateForSearch;
    }

    public void setPressureForSearch(float pressureForSearch) {
        this.pressureForSearch = pressureForSearch;
    }
    public List<T> get(List<T> installations) {
        //выбираем только те установки в которой точки подходят под рабочию
        List<T> resultList = new ArrayList<>();
        for (T installation : installations) {
            if(suitableInstallation(installation, flowRateForSearch, pressureForSearch)){
                resultList.add(installation);
            }
        }
        //сортируем так чтоб были ближе к 60 процентов
        resultList.sort(Comparator.comparingDouble(pump -> getOptimalityScore(pump, flowRateForSearch)));
        return resultList.stream().limit(7).collect(Collectors.toList());
    }

    private boolean suitableInstallation(ParentInstallations installation, float flowRateForSearch, float pressureForSearch) {
        //для каждой установки находим ближайшую точку и если игрек/напор не выше то считаем точку подходящей
        InstallationPoint closePoint = findClosePoint(installation.getInstallationPoints(), flowRateForSearch);
        if(closePoint == null){
            return pressureForSearch  <= closePoint.getY();
        }
        return false;
    }

    private InstallationPoint findClosePoint(List<InstallationPoint> points, float flowRateForSearch) {
        //находим точку ближайшую к рабочей точке
        return points.stream()
                .min(Comparator.comparingDouble(point->
                                Math.abs(point.getX()-flowRateForSearch)))
                .orElse(null);
    }

    private double getOptimalityScore(ParentInstallations installation, double flowRate) {
        // Считаем, насколько близок расход к оптимальному диапазону 60-70%
        double maxFlowRate = installation.getInstallationPoints().stream().max(Comparator.comparingDouble(p -> p.getX())).get().getX();
        return Math.abs(flowRate - 0.65 * maxFlowRate);
    }

    public int getMaxFlowRate() {
        return (int) Math.ceil(((100 * flowRateForSearch) / 60) * 0.25);
    }

    public int getMinFlowRate(){
        return (int) Math.floor(((100 * flowRateForSearch) / 60) * 0.85);
    }
}
