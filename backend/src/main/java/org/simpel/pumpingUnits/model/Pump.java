package org.simpel.pumpingUnits.model;

import jakarta.persistence.*;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.ControlType;
import org.simpel.pumpingUnits.model.enums.Diameter;
import org.simpel.pumpingUnits.model.enums.subtypes.PowerType;
import org.simpel.pumpingUnits.model.enums.subtypes.PumpType;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Entity
public class Pump {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private PumpType type;
    private String manufacturer;
    private int speed;
    private int numberOfSteps;
    //Максимальное давление (как я понял это и есть расход Q)
    private int maximumPressure;
    //Максимальный напор
    private float maximumHead;
    // как я понимаю диаметры должны быть и у устнановок и у насосов
    // заполняется автоматически в зависимотсти от расхода
    private Diameter diameter;
    private String article;
    private float price;
    /*private float power;*/
    private int efficiency;
    private float NPSH;
    private float DM_in;
    private float DM_out;
    private float installationLength;
    private String description;
    @ManyToOne
    @JoinColumn(name = "material_name", referencedColumnName = "name")
    private Material material;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private Engine engine;

    public void setFieldsForPumpSave(InstallationSaveRequest request) {
        this.setName(request.getName());
        this.setType(PumpType.valueOf(request.getPumpType()));
        /*this.setManufacturer(request.getManufactureForPump());
        this.setSpeed(request.getSpeed());
        this.setNumberOfSteps(request.getNumberOfSteps());*/
        this.setMaximumPressure(request.getFlowRate());
        this.setMaximumHead(request.getPressure());
        this.setArticle(request.getArticle());
        this.setPrice(request.getPrice());
        this.setEfficiency(request.getEfficiency());
        this.setNPSH(request.getNPSH());
        this.setDM_in(request.getDM_in());
        this.setDM_out(request.getDM_out());
        this.setEngine(new Engine());
        this.setInstallationLength(request.getInstallationLength());
        this.setDescription(request.getDescription());
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getInstallationLength() {
        return installationLength;
    }

    public void setInstallationLength(float installationLength) {
        this.installationLength = installationLength;
    }

    public float getDM_out() {
        return DM_out;
    }

    public void setDM_out(float DM_out) {
        this.DM_out = DM_out;
    }

    public float getDM_in() {
        return DM_in;
    }

    public void setDM_in(float DM_in) {
        this.DM_in = DM_in;
    }

    public float getNPSH() {
        return NPSH;
    }

    public void setNPSH(float NPSH) {
        this.NPSH = NPSH;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Diameter getDiameter() {
        return diameter;
    }

    public void setDiameter(Diameter diameter) {
        this.diameter = diameter;
    }

    public float getMaximumHead() {
        return maximumHead;
    }

    public void setMaximumHead(float maximumHead) {
        this.maximumHead = maximumHead;
    }

    public int getMaximumPressure() {
        return maximumPressure;
    }

    public void setMaximumPressure(int maximumPressure) {
        if (maximumPressure >= 0 && maximumPressure < 16) {
            this.diameter = Diameter.DN50;
        }
        else if (maximumPressure >= 16 && maximumPressure < 27) {
            this.diameter = Diameter.DN65;
        }
        else if (maximumPressure >= 27 && maximumPressure < 38) {
            this.diameter = Diameter.DN80;
        }
        else if (maximumPressure >= 38 && maximumPressure < 64) {
            this.diameter = Diameter.DN100;
        }
        else if (maximumPressure >= 64 && maximumPressure < 98) {
            this.diameter = Diameter.DN125;
        }
        else if (maximumPressure >= 98 && maximumPressure < 143) {
            this.diameter = Diameter.DN150;
        }
        else if (maximumPressure >= 143 && maximumPressure < 243) {
            this.diameter = Diameter.DN200;
        }
        else if (maximumPressure >= 243 && maximumPressure < 383) {
            this.diameter = Diameter.DN250;
        }
        else if (maximumPressure >= 383 && maximumPressure < 542) {
            this.diameter = Diameter.DN300;
        }
        else if (maximumPressure >= 542 && maximumPressure < 652) {
            this.diameter = Diameter.DN350;
        }
        this.maximumPressure = maximumPressure;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public PumpType getType() {
        return type;
    }

    public void setType(PumpType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
