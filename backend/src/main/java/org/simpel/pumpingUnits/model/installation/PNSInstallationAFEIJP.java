package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpType;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;

@Entity
public class PNSInstallationAFEIJP extends ParentInstallations{
    private CoolantType coolantType;
    private PNSSubtypes subtypes;
    private int totalCapacityOfJockeyPump;
    private int requiredJockeyPumpPressure;
    @Min(value = 4)
    @Max(value = 50)
    private int temperature;


    @Override
    public PNSSubtypes getSubtype() {
        return PNSSubtypes.AFEIJP;
    }

    @Override
    public void setSubtypes(Enum<?> subtype) {
        if (subtype != PNSSubtypes.AFEIJP) {
            throw new IllegalArgumentException("Invalid subtype");
        }
        this.subtypes = (PNSSubtypes) subtype;
    }

    @Override
    public CoolantType getCoolantType() {
        if (coolantType == null) {
            return CoolantType.WATER;
        }
        return coolantType;
    }

    @Override
    public void setCoolantType(CoolantType coolantType) {
        if(coolantType != CoolantType.WATER) {
            throw new IllegalArgumentException("PNSInstallation supports only water coolant");
        }
        this.coolantType = coolantType;
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
