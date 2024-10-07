package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpTypeForSomeInstallation;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;

@Entity
public class HozPitInstallation extends ParentInstallations {
    @Enumerated(EnumType.STRING)
    private CoolantType coolantType;
    @Enumerated(EnumType.STRING)
    private HozPitSubtypes subtype;
    @Enumerated(EnumType.STRING)
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
        this.setPumpType(PumpTypeForSomeInstallation.valueOf(request.getPumpTypeForSomeInstallation()));
        this.setTemperature(request.getTemperature());
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


    public int getTemperature() {
        return temperature;
    }


    public void setTemperature(int temperature) {
        if(temperature < 4 || temperature > 70){
            throw new IllegalArgumentException("HozPitInstallation supports only 4 or 70");
        }
        this.temperature = temperature;
    }
    @Override
    public void setName() {
        String form = "Насосная станция BPS-W-%s%d%d-%s";
        this.name = String.format(form,this.subtype.getCode(),
                this.getCountMainPumps() + this.getCountSparePumps(),
                this.getCountSparePumps(),
                this.getPumps().get(0).getName());
    }
}
