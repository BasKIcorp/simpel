package org.simpel.pumpingUnits.model.installation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ParentInstallations {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "installation_photos", joinColumns = @JoinColumn(name = "installation_id"))
    @Column(name = "file_name")
    private List<String> drawingsPath = new ArrayList<>();
    private TypeInstallations typeInstallations;
    private int temperature;
    private int countMainPumps;
    private int countSparePumps;
    private int FlowRate;
    private int Pressure;
    @OneToMany(mappedBy = "parentInstallations", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InstallationPoint> installationPoints;
    //full info
    private String name;
    private ControlType controlType;
    private PowerType powerType;
    private String article;
    private float price;
    private float power;
    private int efficiency;
    private float NPSH;
    private float DM_in;
    private float DM_out;
    private float installationLength;
    private String description;

    

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

    public abstract void setSubtypes(Enum<?> subtype);

    public abstract CoolantType getCoolantType();

    public abstract void setCoolantType(CoolantType coolantType);

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

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

    public int getFlowRate() {
        return FlowRate;
    }

    public void setFlowRate(int flowRate) {
        FlowRate = flowRate;
    }

    public int getPressure() {
        return Pressure;
    }

    public void setPressure(int pressure) {
        Pressure = pressure;
    }

    public List<InstallationPoint> getInstallationPoints() {
        return installationPoints;
    }

    public void setInstallationPoints(List<InstallationPoint> installationPoints) {
        this.installationPoints = installationPoints;
    }
}
