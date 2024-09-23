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
    @Query("SELECT i FROM PNSInstallationERW i " +
            "WHERE i.typeInstallations = :typeInstallations " +
            "AND i.subtype = :subtype " +
            "AND i.coolantType = :coolantType " +
            "AND i.temperature = :temperature " +
            "AND i.countMainPumps = :countMainPumps " +
            "AND i.countSparePumps = :countSparePumps " +
            "AND i.pumpTypeForSomeInstallation = :pumpTypeForSomeInstallation " +
            "AND i.flowRate BETWEEN :minFlowRate AND :maxFlowRate ")
    public List<PNSInstallationERW> findInstallations(@Param("typeInstallations") TypeInstallations typeInstallations,
                                                      @Param("subtype") PNSSubtypes subtype,
                                                      @Param("coolantType") CoolantType coolantType,
                                                      @Param("temperature") Integer temperature,
                                                      @Param("countMainPumps") Integer countMainPumps,
                                                      @Param("countSparePumps") Integer countSparePumps,
                                                      @Param("pumpTypeForSomeInstallation") PumpTypeForSomeInstallation pumpTypeForSomeInstallation,
                                                      @Param("minFlowRate") Integer minFlowRate,
                                                      @Param("maxFlowRate") Integer maxFlowRate);
}
