package org.simpel.pumpingUnits.controller.installationsUtilsModel;

public class InstallationRequest {
    private String typeInstallations;
    private String subtype;
    private String coolantType;
    private Integer temperature;
    private Integer countMainPumps;
    private Integer countSparePumps;
    private Integer flowRate;
    private Integer pressure;

    //gm
    private Integer concentration;
    //hozPit and PnsERW
    private String pumpTypeForSomeInstallation;
    //PnsAFEIJP
    private Integer totalCapacityOfJockeyPump;
    private Integer requiredJockeyPumpPressure;

    public String getTypeInstallations() {
        return typeInstallations;
    }

    public void setTypeInstallations(String typeInstallations) {
        this.typeInstallations = typeInstallations;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCoolantType() {
        return coolantType;
    }

    public void setCoolantType(String coolantType) {
        this.coolantType = coolantType;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getCountMainPumps() {
        return countMainPumps;
    }

    public void setCountMainPumps(int countMainPumps) {
        this.countMainPumps = countMainPumps;
    }

    public int getCountSparePumps() {
        return countSparePumps;
    }

    public void setCountSparePumps(int countSparePumps) {
        this.countSparePumps = countSparePumps;
    }

    public int getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public Integer getConcentration() {
        return concentration;
    }

    public void setConcentration(Integer concentration) {
        this.concentration = concentration;
    }

    public String getPumpTypeForSomeInstallation() {
        return pumpTypeForSomeInstallation;
    }

    public void setPumpTypeForSomeInstallation(String pumpTypeForSomeInstallation) {
        this.pumpTypeForSomeInstallation = pumpTypeForSomeInstallation;
    }

    public int getTotalCapacityOfJockeyPump() {
        return totalCapacityOfJockeyPump;
    }

    public void setTotalCapacityOfJockeyPump(int totalCapacityOfJockeyPump) {
        this.totalCapacityOfJockeyPump = totalCapacityOfJockeyPump;
    }

    public int getRequiredJockeyPumpPressure() {
        return requiredJockeyPumpPressure;
    }

    public void setRequiredJockeyPumpPressure(int requiredJockeyPumpPressure) {
        this.requiredJockeyPumpPressure = requiredJockeyPumpPressure;
    }

}
