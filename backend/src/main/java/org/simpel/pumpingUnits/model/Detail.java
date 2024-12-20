package org.simpel.pumpingUnits.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Id;


@Entity
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String descriptionDetail;
    private String materialDetail;
    private String enDin;
    private String aisiAstm;


    @ManyToOne
    @JoinColumn(name = "pumps_id", nullable = false)
    @JsonBackReference
    private Pump pump;

    public Pump getPump() {
        return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
    }

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


}
