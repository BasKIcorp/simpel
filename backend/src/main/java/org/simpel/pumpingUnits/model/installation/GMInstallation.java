package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;


import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.validation.ValidTemperature;

@Entity
@ValidTemperature
public class GMInstallation extends ParentInstallations {
    private CoolantType coolantType;
    private SubtypeForGm subtype;
    private Integer concentration;
    private int temperature;

    public GMInstallation(CoolantType coolantType, SubtypeForGm subtype, Integer concentration, int temperature) {
        this.coolantType = coolantType;
        this.subtype = subtype;
        this.concentration = concentration;
        this.temperature = temperature;
    }

    public GMInstallation() {

    }

    @Override
    public SubtypeForGm getSubtype() {
        return subtype;
    }

    @Override
    public void setSubtypes(Enum<?> subtype) {
        this.subtype = (SubtypeForGm) subtype;
    }

    @Override
    public CoolantType getCoolantType() {
        return coolantType;
    }

    @Override
    public void setCoolantType(CoolantType coolantType) {
        this.coolantType = coolantType;
    }

    public Integer getConcentration() {
        if (coolantType == CoolantType.WATER) {
            return null;
        }
        return concentration;
    }

    public void setConcentration(int concentration) {
        if(coolantType == CoolantType.WATER) {
            throw new IllegalArgumentException("FLKDSJNFKLJDSFLKF");
        }
        this.concentration = concentration;
    }

    @Override
    public void setTemperature(int temperature) {
        if (this.coolantType == CoolantType.WATER) {
            if (temperature < 4 || temperature > 70) {
                throw new IllegalArgumentException("Temperature should be between 4 and 70 degrees Celsius for Water");
            }
        } else if (temperature < -10 || temperature > 70) {
            throw new IllegalArgumentException("Temperature should be between -10 and 70 degrees Celsius for Solution");
        }
        else if(this.coolantType == null) {
            throw new IllegalArgumentException("Choose CoolantType before setting the temperature");
        }
        this.temperature = temperature;
    }
}
