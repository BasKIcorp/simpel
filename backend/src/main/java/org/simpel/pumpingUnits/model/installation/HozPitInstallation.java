package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpType;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;

@Entity
public class HozPitInstallation extends ParentInstallations {
    private CoolantType coolantType;
    private HozPitSubtypes subtypes;
    private PumpType pumpType;
    private int temperature;

    @Override
    public CoolantType getCoolantType() {
        if (coolantType == null) {
            return CoolantType.WATER;
        }
        return coolantType;
    }

    @Override
    public void setCoolantType(CoolantType coolantType) {
        if(coolantType != CoolantType.WATER){
            throw new IllegalArgumentException("HozPitInstallation supports only water coolant");
        }
            this.coolantType = coolantType;
    }

    @Override
    public HozPitSubtypes getSubtype() {
        return subtypes;
    }

    @Override
    public void setSubtypes(Enum<?> subtype) {
        this.subtypes = (HozPitSubtypes) subtype;
    }

    public PumpType getPumpType() {
        return pumpType;
    }

    public void setPumpType(PumpType pumpType) {
        this.pumpType = pumpType;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int temperature) {
        if(temperature < 4 || temperature > 70){
            throw new IllegalArgumentException("HozPitInstallation supports only 4 or 70");
        }
        this.temperature = temperature;
    }
}
