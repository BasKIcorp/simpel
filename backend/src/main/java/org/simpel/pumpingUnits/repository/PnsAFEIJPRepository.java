package org.simpel.pumpingUnits.repository;

import org.hibernate.annotations.Parent;
import org.simpel.pumpingUnits.model.installation.PNSInstallationAFEIJP;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface PnsAFEIJPRepository extends JpaRepository<PNSInstallationAFEIJP, Long> {
    @Query("SELECT i FROM PNSInstallationAFEIJP i " +
            "WHERE i.typeInstallations = :typeInstallations " +
            "AND i.subtype = :subtype " +
            "AND i.coolantType = :coolantType " +
            "AND i.temperature = :temperature " +
            "AND i.countMainPumps = :countMainPumps " +
            "AND i.countSparePumps = :countSparePumps " +
            "AND i.totalCapacityOfJockeyPump = :totalCapacityOfJockeyPump "+
            "AND i.requiredJockeyPumpPressure = :requiredJockeyPumpPressure " +
            "AND i.flowRate BETWEEN :minFlowRate AND :maxFlowRate ")
    public List<PNSInstallationAFEIJP> findInstallations(@Param("typeInstallations") String typeInstallations,
                                                      @Param("subtype") String subtype,
                                                      @Param("coolantType") String coolantType,
                                                      @Param("temperature") Integer temperature,
                                                      @Param("countMainPumps") Integer countMainPumps,
                                                      @Param("countSparePumps") Integer countSparePumps,
                                                      @Param("totalCapacityOfJockeyPump") Integer totalCapacityOfJockeyPump,
                                                      @Param("requiredJockeyPumpPressure") Integer requiredJockeyPumpPressure,
                                                      @Param("minFlowRate") Integer minFlowRate,
                                                      @Param("maxFlowRate") Integer maxFlowRate);
}
