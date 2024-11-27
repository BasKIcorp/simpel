package org.simpel.pumpingUnits.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;


@Entity
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String descriptionDetail;
    private String materialDetail;
    private String enDin;
    private String aisiAstm;

    @ManyToOne
    @JoinColumn(name = "pump_name", referencedColumnName = "name", nullable = false)
    private Pump pumpName;

    public String getDescriptionDetail() {
        return descriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        this.descriptionDetail = descriptionDetail;
    }

    public String getMaterialDetail() {
        return materialDetail;
    }

    public void setMaterialDetail(String materialDetail) {
        this.materialDetail = materialDetail;
    }

    public String getEnDin() {
        return enDin;
    }

    public void setEnDin(String enDin) {
        this.enDin = enDin;
    }

    public String getAisiAstm() {
        return aisiAstm;
    }

    public void setAisiAstm(String aisiAstm) {
        this.aisiAstm = aisiAstm;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public Pump getPumpName() {
        return pumpName;
    }

    public void setPumpName(Pump pumpName) {
        this.pumpName = pumpName;
    }
}
