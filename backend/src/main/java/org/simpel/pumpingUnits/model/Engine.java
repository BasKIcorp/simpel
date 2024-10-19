package org.simpel.pumpingUnits.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.subtypes.PumpType;

@Entity
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String manufacturer;
    private String execution;
    private PumpType pumpType;
    private float power;
    private float amperage;
    private float voltage;
    private int turnovers;
    private String typeOfProtection;
    private String insulationClass;
    private String color;
    public Engine() {}
    public void setFieldsForPumpSave(Engine request){
        this.manufacturer = request.getManufacturer();
        this.execution = request.getExecution();
        this.pumpType = PumpType.valueOf(request.getPumpType().toString());
        this.power = request.getPower();
        this.amperage = request.getAmperage();
        this.turnovers = request.getTurnovers();
        this.typeOfProtection = request.getTypeOfProtection();
        this.insulationClass = request.getInsulationClass();
        this.color = request.getColor();
    }
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public PumpType getPumpType() {
        return pumpType;
    }

    public void setPumpType(String pumpType) {
        this.pumpType = PumpType.valueOf(pumpType);
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getAmperage() {
        return amperage;
    }

    public void setAmperage(float amperage) {
        this.amperage = amperage;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(int turnovers) {
        this.turnovers = turnovers;
    }

    public String getTypeOfProtection() {
        return typeOfProtection;
    }

    public void setTypeOfProtection(String typeOfProtection) {
        this.typeOfProtection = typeOfProtection;
    }

    public String getInsulationClass() {
        return insulationClass;
    }

    public void setInsulationClass(String insulationClass) {
        this.insulationClass = insulationClass;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
