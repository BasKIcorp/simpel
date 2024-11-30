package org.simpel.pumpingUnits.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.simpel.pumpingUnits.model.enums.PhotoType;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // Уникальный идентификатор

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "photo_type")
    @Enumerated(EnumType.STRING)
    private PhotoType photoType;

    @ManyToOne
    @JoinColumn(name = "pump_id", nullable = false)
    @JsonBackReference
    private Pump pump; // Связь с Pump

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public Pump getPump() {
        return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
    }
}