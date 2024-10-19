package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GMRepository extends JpaRepository<GMInstallation, Long> {
    List<GMInstallation> findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndConcentrationAndCountMainPumpsAndCountSparePumpsAndFlowRateBetween(
            TypeInstallations typeInstallations,
            SubtypeForGm subtype,
            CoolantType coolantType,
            int temperature,
            Integer concentration,
            int countMainPumps,
            int countSparePumps,
            int minFlowRate,
            int maxFlowRate
    );
}
