package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpTypeForSomeInstallation;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;

@Entity
public class HozPitInstallation extends ParentInstallations {
    private CoolantType coolantType;
    private HozPitSubtypes subtype;
    private PumpTypeForSomeInstallation pumpTypeForSomeInstallation;
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
    public void setSpecificFields(InstallationRequest request) {
        this.setSubtype(HozPitSubtypes.valueOf(request.getSubtype()));
        this.setPumpType(PumpTypeForSomeInstallation.valueOf(request.getPumpType()));
    }

    @Override
    public HozPitSubtypes getSubtype() {
        return subtype;
    }

    @Override
    public void setSubtype(Enum<?> subtype) {
        this.subtype = (HozPitSubtypes) subtype;
    }

    public PumpTypeForSomeInstallation getPumpType() {
        return pumpTypeForSomeInstallation;
    }

    public void setPumpType(PumpTypeForSomeInstallation pumpTypeForSomeInstallation) {
        this.pumpTypeForSomeInstallation = pumpTypeForSomeInstallation;
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
