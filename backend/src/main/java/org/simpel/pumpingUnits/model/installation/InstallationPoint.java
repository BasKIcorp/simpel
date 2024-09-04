package org.simpel.pumpingUnits.model.installation;

import jakarta.persistence.*;

@Entity
public class InstallationPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ParentInstallations parentInstallations;

    private double x;
    private double y;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParentInstallations getParentInstallations() {
        return parentInstallations;
    }

    public void setParentInstallations(ParentInstallations parentInstallations) {
        this.parentInstallations = parentInstallations;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
