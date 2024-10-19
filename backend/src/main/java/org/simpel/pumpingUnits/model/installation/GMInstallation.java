package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.*;


import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;

@Entity
public class GMInstallation extends ParentInstallations {
    @Enumerated(EnumType.STRING)
    private CoolantType coolantType;
    @Enumerated(EnumType.STRING)
    private SubtypeForGm subtype;
    private Integer concentration;
    @Column(name = "temperature")
    private int temperature;

    @Override
    public void setSpecificFields(InstallationRequest request) {
        this.setSubtype(SubtypeForGm.valueOf(request.getSubtype()));
        this.setConcentration(request.getConcentration());
        this.setTemperature(request.getTemperature());

    }

    @Override
    public SubtypeForGm getSubtype() {
        return subtype;
    }

    @Override
    public void setSubtype(Enum<?> subtype) {
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

    public void setConcentration(Integer concentration) {
        if (coolantType == CoolantType.WATER && concentration != null) {
            throw new IllegalArgumentException("Water should not have concentration");
        } else if (coolantType != CoolantType.WATER && concentration == null) {
            throw new IllegalArgumentException("Concentration should not be null for Solution");
        }

        this.concentration = concentration;
    }


    public void setTemperature(int temperature) {
        if (this.coolantType == CoolantType.WATER) {
            if (temperature < 4 || temperature > 70) {
                throw new IllegalArgumentException("Temperature should be between 4 and 70 degrees Celsius for Water");
            }
        } else if (temperature < -10 || temperature > 70) {
            throw new IllegalArgumentException("Temperature should be between -10 and 70 degrees Celsius for Solution");
        } else if (this.coolantType == null) {
            throw new IllegalArgumentException("Choose CoolantType before setting the temperature");
        }
        this.temperature = temperature;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }


    public void setName() {
        String form = "Насосная станция BPS-C%s%d%d-%s";
        this.name = String.format(form, this.subtype.getCode(),
                this.getCountMainPumps() + this.getCountSparePumps(), this.getCountSparePumps(),
                this.getPumps().get(0).getName());
    }

}
