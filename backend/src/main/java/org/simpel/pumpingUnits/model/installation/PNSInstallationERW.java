package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpType;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;

@Entity
public class PNSInstallationERW extends ParentInstallations {
    private CoolantType coolantType;
    private PNSSubtypes subtypes;
    private PumpType pumpType;
    @Min(value = 4)
    @Max(value = 50)
    private int temperature;

    @Override
    public PNSSubtypes getSubtype() {
        return PNSSubtypes.ERW_SYSTEM;
    }

    @Override
    public void setSubtypes(Enum<?> subtype) {
        if (subtype != PNSSubtypes.ERW_SYSTEM) {
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

    public PumpType getPumpType() {
        return pumpType;
    }

    public void setPumpType(PumpType pumpType) {
        this.pumpType = pumpType;
    }
}
