package org.simpel.pumpingUnits.controller.installationsUtilsModel;

import org.simpel.pumpingUnits.model.enums.TypeInstallations;

public class ListInstallations {
    private Long id;
    private String name;
    private TypeInstallations type;
    private Enum<?> subType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeInstallations getType() {
        return type;
    }

    public void setType(TypeInstallations type) {
        this.type = type;
    }

    public Enum<?> getSubType() {
        return subType;
    }

    public void setSubType(Enum<?> subType) {
        this.subType = subType;
    }
}



