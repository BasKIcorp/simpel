package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GMRepository extends JpaRepository<GMInstallation, Long> {
    @Query("SELECT i FROM GMInstallation i " +
            "WHERE i.typeInstallations = :typeInstallations " +
            "AND i.subtype = :subtype " +
            "AND i.coolantType = :coolantType " +
            "AND i.temperature = :temperature " +
            "AND i.concentration = :concentration " +
            "AND i.countMainPumps = :countMainPumps " +
            "AND i.countSparePumps = :countSparePumps " +
            "AND i.flowRate BETWEEN :minFlowRate AND :maxFlowRate "
    )
    public List<GMInstallation> findInstallations(@Param("typeInstallations") String typeInstallations,
                                                  @Param("subtype") String subtype,
                                                  @Param("coolantType") String coolantType,
                                                  @Param("temperature") Integer temperature,
                                                  @Param("concentration") Integer concentration,
                                                  @Param("countMainPumps") Integer countMainPumps,
                                                  @Param("countSparePumps") Integer countSparePumps,
                                                  @Param("minFlowRate") Integer minFlowRate,
                                                  @Param("maxFlowRate") Integer maxFlowRate);

}
