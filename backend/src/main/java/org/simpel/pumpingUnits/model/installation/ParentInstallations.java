package org.simpel.pumpingUnits.model.installation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.ControlType;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.Diameter;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PowerType;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ParentInstallations {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "installation_photos",
            joinColumns = @JoinColumn(name = "installation_id"))
    @Column(name = "file_name")
    private List<String> drawingsPath = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private TypeInstallations typeInstallations;
    private int countMainPumps;
    private int countSparePumps;
    private float flowRate;
    private float pressure;
    @Enumerated(EnumType.STRING)
    private ControlType controlType;
    @Enumerated(EnumType.STRING)
    private PowerType powerType;
    @Enumerated(EnumType.STRING)
    private Diameter diameter;
    protected String name;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "installation_pump",
            joinColumns = @JoinColumn(name = "installation_id"),
            inverseJoinColumns = @JoinColumn(name = "pump_id"))
    @JsonManagedReference
    private List<Pump> pumps = new ArrayList<>();

    public List<Pump> getPumps() {
        return pumps;
    }

    public void setPumps(List<Pump> pumps) {
        this.pumps = pumps;
    }

    //full info
    public void setCommonFields(InstallationRequest request) {
        this.setTypeInstallations(TypeInstallations.valueOf(request.getTypeInstallations()));
        this.setCoolantType(CoolantType.valueOf(request.getCoolantType()));
        this.setCountMainPumps(request.getCountMainPumps());
        this.setCountSparePumps(request.getCountSparePumps());
    }

    public abstract void setSpecificFields(InstallationRequest request);

    public void setFieldsForSave(InstallationSaveRequest request, MultipartFile[] files, FileStorageService fileStorageService) throws IOException {
        this.setPrice(request.getPrice() + pumps.get(0).getPrice() + (pumps.size() > 1 ? pumps.get(1).getPrice() : 0));
        List<String> pathFiles = fileStorageService.saveFiles(files, request.getTypeInstallations(), request.getSubtype());
        this.setDrawingsPath(pathFiles);
        this.setControlType(ControlType.valueOf(request.getControlType()));
        this.setPowerType(PowerType.valueOf(request.getPowerType()));
        this.setControlType(ControlType.valueOf(request.getControlType()));
        this.setPowerType(PowerType.valueOf(request.getPowerType()));
        this.setFlowRate(pumps.get(0).getMaximumPressure());
        this.setPressure(pumps.get(0).getMaximumHead());
        this.setName();

    }


    public String getName() {
        return name;
    }

    public abstract void setName();


    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    public PowerType getPowerType() {
        return powerType;
    }

    public void setPowerType(PowerType powerType) {
        this.powerType = powerType;
    }

    public Long getId() {
        return id;
    }

    public List<String> getDrawingsPath() {
        return drawingsPath;
    }

    public void setDrawingsPath(List<String> drawingsPath) {
        this.drawingsPath = drawingsPath;
    }

    public void addDrawingsPath(String name) {
        this.drawingsPath.add(name);
    }

    public void removeDrawingsPath(String name) {
        this.drawingsPath.remove(name);
    }

    public TypeInstallations getTypeInstallations() {
        return typeInstallations;
    }

    public void setTypeInstallations(TypeInstallations typeInstallations) {
        this.typeInstallations = typeInstallations;
    }

    public abstract Enum<?> getSubtype();

    public abstract void setSubtype(Enum<?> subtype);

    public abstract CoolantType getCoolantType();

    public abstract void setCoolantType(CoolantType coolantType);

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

    public float getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(float flowRate) {
        if (flowRate >= 0 && flowRate < 16) {
            this.diameter = Diameter.DN50;
        } else if (flowRate >= 16 && flowRate < 27) {
            this.diameter = Diameter.DN65;
        } else if (flowRate >= 27 && flowRate < 38) {
            this.diameter = Diameter.DN80;
        } else if (flowRate >= 38 && flowRate < 64) {
            this.diameter = Diameter.DN100;
        } else if (flowRate >= 64 && flowRate < 98) {
            this.diameter = Diameter.DN125;
        } else if (flowRate >= 98 && flowRate < 143) {
            this.diameter = Diameter.DN150;
        } else if (flowRate >= 143 && flowRate < 243) {
            this.diameter = Diameter.DN200;
        } else if (flowRate >= 243 && flowRate < 383) {
            this.diameter = Diameter.DN250;
        } else if (flowRate >= 383 && flowRate < 542) {
            this.diameter = Diameter.DN300;
        } else if (flowRate >= 542 && flowRate < 652) {
            this.diameter = Diameter.DN350;
        }
        this.flowRate = flowRate;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public Diameter getDiameter() {
        return diameter;
    }

    public abstract int getTemperature();
}
