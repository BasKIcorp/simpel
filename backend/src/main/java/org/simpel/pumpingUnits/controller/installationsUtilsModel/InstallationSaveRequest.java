package org.simpel.pumpingUnits.controller.installationsUtilsModel;

import org.simpel.pumpingUnits.model.enums.ControlType;
import org.simpel.pumpingUnits.model.enums.subtypes.PowerType;

public class InstallationSaveRequest extends InstallationRequest {
    //разница между запросом на сохранение и запросом на гет в том что в запросе на сохранение надо вставлять больше информации поэтому унаследовался от запроса на гет
    private String name;
    private String controlType;
    private String powerType;
    private String article;
    private Float price;
    private Float power;
    private Integer efficiency;
    private Float NPSH;
    private Float DM_in;
    private Float DM_out;
    private Float installationLength;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPower() {
        return power;
    }

    public void setPower(Float power) {
        this.power = power;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public Float getNPSH() {
        return NPSH;
    }

    public void setNPSH(Float NPSH) {
        this.NPSH = NPSH;
    }

    public Float getDM_in() {
        return DM_in;
    }

    public void setDM_in(Float DM_in) {
        this.DM_in = DM_in;
    }

    public Float getDM_out() {
        return DM_out;
    }

    public void setDM_out(Float DM_out) {
        this.DM_out = DM_out;
    }

    public Float getInstallationLength() {
        return installationLength;
    }

    public void setInstallationLength(Float installationLength) {
        this.installationLength = installationLength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
