package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpTypeForSomeInstallation;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;

@Entity
public class PNSInstallationERW extends ParentInstallations {
    private CoolantType coolantType;
    private PNSSubtypes subtype;
    private PumpTypeForSomeInstallation pumpTypeForSomeInstallation;
    private int temperature;

    @Override
    public PNSSubtypes getSubtype() {
        return PNSSubtypes.ERW_SYSTEM;
    }

    @Override
    public void setSubtype(Enum<?> subtype) {
        if (subtype != PNSSubtypes.ERW_SYSTEM) {
            throw new IllegalArgumentException("Invalid subtype");
        }
        this.subtype = (PNSSubtypes) subtype;
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

    @Override
    public void setSpecificFields(InstallationRequest request) {
        this.setSubtype(PNSSubtypes.valueOf(request.getSubtype()));
        this.setTemperature(request.getTemperature());
        this.setPumpType(PumpTypeForSomeInstallation.valueOf(request.getPumpTypeForSomeInstallation()));
    }

    public PumpTypeForSomeInstallation getPumpType() {
        return pumpTypeForSomeInstallation;
    }

    public void setPumpType(PumpTypeForSomeInstallation pumpTypeForSomeInstallation) {
        this.pumpTypeForSomeInstallation = pumpTypeForSomeInstallation;
    }

    public int getTemperature() {
        return temperature;
    }


    public void setTemperature(int temperature) {
        if(temperature < 4 || temperature > 50){
            throw new IllegalArgumentException("PNSInstallationERW supports only 4 or 50");
        }
        this.temperature = temperature;
    }
}
