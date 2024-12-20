package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;

@Entity
public class PNSInstallationAFEIJP extends ParentInstallations{
    @Enumerated(EnumType.STRING)
    private CoolantType coolantType;
    @Enumerated(EnumType.STRING)
    private PNSSubtypes subtype;
    private int totalCapacityOfJockeyPump;
    private int requiredJockeyPumpPressure;
    private int temperature;


    @Override
    public PNSSubtypes getSubtype() {
        return PNSSubtypes.AFEIJP;
    }

    public void setSubtype(Enum<?> subtype) {
        if (subtype != PNSSubtypes.AFEIJP) {
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
//        this.setTotalCapacityOfJockeyPump(request.getTotalCapacityOfJockeyPump());
//        this.setRequiredJockeyPumpPressure(request.getRequiredJockeyPumpPressure());
        if(!getPumps().isEmpty()){
            this.setTotalCapacityOfJockeyPump(getPumps().get(1).getMaximumPressure());
            this.setRequiredJockeyPumpPressure((int) getPumps().get(1).getMaximumHead());
        }
        this.setTemperature(request.getTemperature());

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


    public int getTemperature() {
        return temperature;
    }



    public void setTemperature(int temperature) {
        if(temperature < 4 || temperature > 50){
            throw new IllegalArgumentException("PNSInstallationAFEIJP supports only 4 or 50");
        }
        this.temperature = temperature;
    }

    @Override
    public void setName() {
        String form = "Насосная станция пожаротушения FPS-%s%d%d-%s+%s";
        this.name = String.format(form,this.subtype.getCode(),
                this.getCountMainPumps() + this.getCountSparePumps(),
                this.getCountSparePumps(),
                this.getPumps().get(0).getName(),
                this.getPumps().get(1).getName());
    }
}
