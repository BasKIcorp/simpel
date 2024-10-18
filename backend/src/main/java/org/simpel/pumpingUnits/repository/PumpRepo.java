package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.Pump;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PumpRepo extends JpaRepository<Pump, Long> {
    Optional<Pump> findByName(String namePump);
}
