package org.simpel.pumpingUnits.controller.installationsUtilsModel;

import java.util.List;

public class InstallationSaveRequest extends InstallationRequest {
    //для установок
    private String controlType;
    private String powerType;
    private String nameForInstallation;

    public String getNameForInstallation() {
        return nameForInstallation;
    }

    public void setNameForInstallation(String nameForInstallation) {
        this.nameForInstallation = nameForInstallation;
    }

    //разница между запросом на сохранение и запросом на гет в том что в запросе на сохранение надо вставлять больше информации поэтому унаследовался от запроса на гет
    //для насосов
    private String namePump;
    //берем из движка/**/
    /*private String pumpType;*/
    private String manufacturerForPump;
    private Integer speed;
    private Integer numberOfSteps;
    //Максимальное давление (как я понял это и есть расход Q)
    private Integer maximumPressure;
    //Максимальный напор
    private Float maximumHead;
    // как я понимаю диаметры должны быть и у устнановок и у насосов
    // заполняется автоматически в зависимотсти от расхода
    private String article;
    private Float price;

    private Integer efficiency;
    private Float npsh;
    private Float dmIn;
    private Float dmOut;
    private Float installationLength;
    private String description;
    private String material;



    //для движков
    private String manufacturerForEngine;
    private String execution;
    private String pumpType;
    private Float power;
    private Float amperage;
    private Float voltage;
    private Integer turnovers;
    private String typeOfProtection;
    private String insulationClass;
    private String color;

    public List<Long> getPumpsID() {
        return pumpsID;
    }

    public void setPumpsID(List<Long> pumpsID) {
        this.pumpsID = pumpsID;
    }

    private List<Long> pumpsID;

    public String getNamePump() {
        return namePump;
    }

    public void setNamePump(String namePump) {
        this.namePump = namePump;
    }
    public String getPumpType() {
        return pumpType;
    }

    public void setPumpType(String pumpType) {
        this.pumpType = pumpType;
    }

    public String getManufacturerForPump() {
        return manufacturerForPump;
    }

    public void setManufacturerForPump(String manufacturerForPump) {
        this.manufacturerForPump = manufacturerForPump;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(Integer numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public Integer getMaximumPressure() {
        return maximumPressure;
    }

    public void setMaximumPressure(Integer maximumPressure) {
        this.maximumPressure = maximumPressure;
    }

    public Float getMaximumHead() {
        return maximumHead;
    }

    public void setMaximumHead(Float maximumHead) {
        this.maximumHead = maximumHead;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPower() {
        return power;
    }

    public void setPower(Float power) {
        this.power = power;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public Float getNpsh() {
        return npsh;
    }

    public void setNpsh(Float npsh) {
        this.npsh = npsh;
    }

    public Float getDmIn() {
        return dmIn;
    }

    public void setDmIn(Float dmIn) {
        this.dmIn = dmIn;
    }

    public Float getDmOut() {
        return dmOut;
    }

    public void setDmOut(Float dmOut) {
        this.dmOut = dmOut;
    }

    public Float getInstallationLength() {
        return installationLength;
    }

    public void setInstallationLength(Float installationLength) {
        this.installationLength = installationLength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getInsulationClass() {
        return insulationClass;
    }

    public void setInsulationClass(String insulationClass) {
        this.insulationClass = insulationClass;
    }

    public String getTypeOfProtection() {
        return typeOfProtection;
    }

    public void setTypeOfProtection(String typeOfProtection) {
        this.typeOfProtection = typeOfProtection;
    }

    public Integer getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(Integer turnovers) {
        this.turnovers = turnovers;
    }

    public Float getVoltage() {
        return voltage;
    }

    public void setVoltage(Float voltage) {
        this.voltage = voltage;
    }

    public Float getAmperage() {
        return amperage;
    }

    public void setAmperage(Float amperage) {
        this.amperage = amperage;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getManufacturerForEngine() {
        return manufacturerForEngine;
    }

    public void setManufacturerForEngine(String manufacturerForEngine) {
        this.manufacturerForEngine = manufacturerForEngine;
    }
}
