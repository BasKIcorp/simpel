package org.simpel.pumpingUnits.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Material {
    @Id
    private String name;
    private String collector;
    private String shutOffValves;
    private String checkValves;
    private String pressureSwitch;
    private String pressureSensors;
    private String plugsOrFlanges;
    private String rack;
    private String frameBase;
    private String pumpHousing;
    private String externalCasing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getShutOffValves() {
        return shutOffValves;
    }

    public void setShutOffValves(String shutOffValves) {
        this.shutOffValves = shutOffValves;
    }

    public String getCheckValves() {
        return checkValves;
    }

    public void setCheckValves(String checkValves) {
        this.checkValves = checkValves;
    }

    public String getPressureSwitch() {
        return pressureSwitch;
    }

    public void setPressureSwitch(String pressureSwitch) {
        this.pressureSwitch = pressureSwitch;
    }

    public String getPressureSensors() {
        return pressureSensors;
    }

    public void setPressureSensors(String pressureSensors) {
        this.pressureSensors = pressureSensors;
    }

    public String getPlugsOrFlanges() {
        return plugsOrFlanges;
    }

    public void setPlugsOrFlanges(String plugsOrFlanges) {
        this.plugsOrFlanges = plugsOrFlanges;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getFrameBase() {
        return frameBase;
    }

    public void setFrameBase(String frameBase) {
        this.frameBase = frameBase;
    }

    public String getPumpHousing() {
        return pumpHousing;
    }

    public void setPumpHousing(String pumpHousing) {
        this.pumpHousing = pumpHousing;
    }

    public String getExternalCasing() {
        return externalCasing;
    }

    public void setExternalCasing(String externalCasing) {
        this.externalCasing = externalCasing;
    }
}
