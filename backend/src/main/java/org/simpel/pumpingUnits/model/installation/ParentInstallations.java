package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.*;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ParentInstallations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TypeInstallations typeInstallations;
    private Enum<?> subtype;
    private CoolantType coolantType;
    private int temperature;
    private int countMainPumps;
    private int countSparePumps;
    private int FlowRate;
    private int Pressure;

    public TypeInstallations getTypeInstallations() {
        return typeInstallations;
    }

    public void setTypeInstallations(TypeInstallations typeInstallations) {
        this.typeInstallations = typeInstallations;
    }

    public abstract Enum<?> getSubtype();

    public abstract void setSubtypes(Enum<?> subtype);

    public abstract CoolantType getCoolantType();

    public abstract void setCoolantType(CoolantType coolantType);

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
        return FlowRate;
    }

    public void setFlowRate(int flowRate) {
        FlowRate = flowRate;
    }

    public int getPressure() {
        return Pressure;
    }

    public void setPressure(int pressure) {
        Pressure = pressure;
    }
}
