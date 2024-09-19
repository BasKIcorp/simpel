package org.simpel.pumpingUnits.model.installation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.Material;
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
    @CollectionTable(name = "installation_photos", joinColumns = @JoinColumn(name = "installation_id"))
    @Column(name = "file_name")
    private List<String> drawingsPath = new ArrayList<>();
    private TypeInstallations typeInstallations;
    private int temperature;
    private int countMainPumps;
    private int countSparePumps;
    private int flowRate;
    private int pressure;
    private ControlType controlType;
    private PowerType powerType;
    @OneToMany(mappedBy = "parentInstallations", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InstallationPoint> installationPoints;
    private Diameter diameter;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "installation_id") // Внешний ключ в таблице насосов
    private List<Pump> pumps = new ArrayList<>();

    public List<Pump> getPumps() {
        return pumps;
    }

    public void setPumps(List<Pump> pumps) {
        this.pumps = pumps;
    }

    //full info
    //ToDo переписать запросы
    //ToDo убрать не нужные поля из отцовской установки
    //ToDo перенсти точки в насосы
    //ToDo сделать 3 разных точки
    //ToDo посмотреть что из движка должно быть и в насосе по типу типа насоса
    //ToDo сервисы переписать
    //ToDo написать гет ручки для насосов и движков
    //ToDo посмотреть сервис подбора
    //ToDo написать методы для заполнения в классе движка
    private String name;

    public void setCommonFields(InstallationRequest request) {
        this.setTypeInstallations(TypeInstallations.valueOf(request.getTypeInstallations()));
        this.setCoolantType(CoolantType.valueOf(request.getCoolantType()));
        this.setTemperature(request.getTemperature());
        this.setCountMainPumps(request.getCountMainPumps());
        this.setCountSparePumps(request.getCountSparePumps());
        this.setFlowRate(request.getFlowRate());
        this.setPressure(request.getPressure());
        this.setControlType(ControlType.valueOf(request.getControlType()));
        this.setPowerType(PowerType.valueOf(request.getPowerType()));
    }

    public abstract void setSpecificFields(InstallationRequest request);

    public void setFieldsForSave(InstallationSaveRequest request, MultipartFile[] files, List<InstallationPoint> points, FileStorageService fileStorageService) throws IOException {
        for(InstallationPoint point : points){
            point.setParentInstallations(this);
        }
        this.setInstallationPoints(points);
        List<String> pathFiles = fileStorageService.saveFiles(files,request.getTypeInstallations(), request.getSubtype());
        this.setDrawingsPath(pathFiles);
        this.setName(request.getName());
        this.setControlType(ControlType.valueOf(request.getControlType()));
        this.setPowerType(PowerType.valueOf(request.getPowerType()));
        this.setPumps(null);
        /*this.setArticle(request.getArticle());
        this.setPrice(request.getPrice());
        this.setPower(request.getPower());
        this.setEfficiency(request.getEfficiency());
        this.setNPSH(request.getNPSH());
        this.setDM_in(request.getDM_in());
        this.setDM_out(request.getDM_out());
        this.setInstallationLength(request.getInstallationLength());
        this.setDescription(request.getDescription());*/
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public List<InstallationPoint> getInstallationPoints() {
        return installationPoints;
    }

    public void setInstallationPoints(List<InstallationPoint> installationPoints) {
        this.installationPoints = installationPoints;
    }
}
