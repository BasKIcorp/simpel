package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.installation.PNSInstallationAFEIJP;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PnsAFEIJPRepository extends JpaRepository<PNSInstallationAFEIJP, Long> {

    List<PNSInstallationAFEIJP> findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndTotalCapacityOfJockeyPumpAndRequiredJockeyPumpPressureAndFlowRateBetween(
            TypeInstallations typeInstallations,
            PNSSubtypes subtype,
            CoolantType coolantType,
            Integer temperature,
            Integer countMainPumps,
            Integer countSparePumps,
            Integer totalCapacityOfJockeyPump,
            Integer requiredJockeyPumpPressure,
            Integer minFlowRate,
            Integer maxFlowRate
    );
}


