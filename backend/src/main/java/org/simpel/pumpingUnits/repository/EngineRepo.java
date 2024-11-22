package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface EngineRepo extends JpaRepository<Engine, Long> {
    Optional<Engine> findByName(String name);
}
