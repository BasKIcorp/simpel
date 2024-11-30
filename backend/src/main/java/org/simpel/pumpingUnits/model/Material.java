package org.simpel.pumpingUnits.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Field;


@Entity
public class Material {
    @Id
    private String name;
    @NotNull
    private String collector;
    @NotNull
    private String shutOffValves;
    @NotNull
    private String checkValves;
    @NotNull
    private String pressureSwitch;
    @NotNull
    private String pressureSensors;
    @NotNull
    private String plugsOrFlanges;
    @NotNull
    private String rack;
    @NotNull
    private String frameBase;
    @NotNull
    private String pumpHousing;
    @NotNull
    private String externalCasing;
    @NotNull
    private boolean StainlessSteel;//Нержавеющая сталь или раб колесо хз
    @NotNull
    private String DischargeSideConnection;//Патрубок на напорной стороне
    @NotNull
    private String SuctionSideConnection; //Патрубок на стороне всасывания

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

    public boolean isStainlessSteel() {
        return StainlessSteel;
    }

    public void setStainlessSteel(boolean stainlessSteel) {
        StainlessSteel = stainlessSteel;
    }

    public String getDischargeSideConnection() {
        return DischargeSideConnection;
    }

    public void setDischargeSideConnection(String dischargeSideConnection) {
        DischargeSideConnection = dischargeSideConnection;
    }

    public String getSuctionSideConnection() {
        return SuctionSideConnection;
    }

    public void setSuctionSideConnection(String suctionSideConnection) {
        SuctionSideConnection = suctionSideConnection;
    }

    public void setFields(Material material){
        try {
            this.setStainlessSteel(material.isStainlessSteel());
            this.setCheckValves(material.getCheckValves());
            this.setCollector(material.getCollector());
            this.setDischargeSideConnection(material.getDischargeSideConnection());
            this.setExternalCasing(material.getExternalCasing());
            this.setFrameBase(material.getFrameBase());
            this.setName(material.getName());
            this.setPlugsOrFlanges(material.getPlugsOrFlanges());
            this.setPressureSensors(material.getPressureSensors());
            this.setPressureSwitch(material.getPressureSwitch());
            this.setPumpHousing(material.getPumpHousing());
            this.setRack(material.getRack());
            this.setShutOffValves(material.getShutOffValves());
            this.setSuctionSideConnection(material.getSuctionSideConnection());
        }
        catch (NullPointerException e) {
            throw new NullPointerException("Material must have all required fields");
        }
    }

}
