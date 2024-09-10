package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.HozPitInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HozPitRepository extends JpaRepository<HozPitInstallation,Long> {
    @Query("SELECT i FROM HozPitInstallation i " +
            "WHERE i.typeInstallations = :typeInstallations " +
            "AND i.subtype = :subtype " +
            "AND i.coolantType = :coolantType " +
            "AND i.temperature = :temperature " +
            "AND i.countMainPumps = :countMainPumps " +
            "AND i.countSparePumps = :countSparePumps " +
            "AND i.pumpType = :pumpType " +
            "AND i.flowRate BETWEEN :minFlowRate AND :maxFlowRate ")
    public List<HozPitInstallation> findInstallations(@Param("typeInstallations") String typeInstallations,
                                                  @Param("subtype") String subtype,
                                                  @Param("coolantType") String coolantType,
                                                  @Param("temperature") Integer temperature,
                                                  @Param("countMainPumps") Integer countMainPumps,
                                                  @Param("countSparePumps") Integer countSparePumps,
                                                  @Param("pumpType") String pumpType,
                                                  @Param("minFlowRate") Integer minFlowRate,
                                                  @Param("maxFlowRate") Integer maxFlowRate);

}
