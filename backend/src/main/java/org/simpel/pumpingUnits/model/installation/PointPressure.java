package org.simpel.pumpingUnits.model.installation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.simpel.pumpingUnits.model.Pump;
import org.springframework.security.core.parameters.P;

@Entity
public class PointPressure extends Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pumps_id", nullable = false)
    @JsonBackReference
    private Pump pump;
    private double x;
    private double y;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pump getPump() {
        return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
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
