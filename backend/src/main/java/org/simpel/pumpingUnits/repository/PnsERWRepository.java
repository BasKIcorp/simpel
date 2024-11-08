package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpTypeForSomeInstallation;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PnsERWRepository extends JpaRepository<PNSInstallationERW, Long> {

    List<PNSInstallationERW> findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndPumpTypeForSomeInstallationAndFlowRateBetween(
            TypeInstallations typeInstallations,
            PNSSubtypes subtype,
            CoolantType coolantType,
            Integer temperature,
            Integer countMainPumps,
            Integer countSparePumps,
            PumpTypeForSomeInstallation pumpTypeForSomeInstallation,
            Integer minFlowRate,
            Integer maxFlowRate
    );
    List<PNSInstallationERW> findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndFlowRateBetween(
            TypeInstallations typeInstallations,
            PNSSubtypes subtype,
            CoolantType coolantType,
            Integer temperature,
            Integer countMainPumps,
            Integer countSparePumps,
            Integer minFlowRate,
            Integer maxFlowRate
    );
}

