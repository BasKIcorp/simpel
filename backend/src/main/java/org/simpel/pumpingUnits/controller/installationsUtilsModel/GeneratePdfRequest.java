package org.simpel.pumpingUnits.controller.installationsUtilsModel;

import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.service.pdfComponents.Options;

public class GeneratePdfRequest {
    private Long installationId;
    private TypeInstallations typeInstallations;
    private String subtype;
    private float x;
    private float y;
    private Options options;

    public Long getInstallationId() {
        return installationId;
    }

    public void setInstallationId(Long installationId) {
        this.installationId = installationId;
    }

    public TypeInstallations getTypeInstallations() {
        return typeInstallations;
    }

    public void setTypeInstallations(TypeInstallations typeInstallations) {
        this.typeInstallations = typeInstallations;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}