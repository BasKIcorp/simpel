package org.simpel.pumpingUnits.controller.pumpRequest;

import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Pump;

public class PumpRequest {

    private Pump pump;
    private Engine engine;
    private String material;
    private String serial;

    public Pump getPump() {
           return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}