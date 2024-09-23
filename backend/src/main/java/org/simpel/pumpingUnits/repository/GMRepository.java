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
    @Query("SELECT i FROM GMInstallation i LEFT JOIN FETCH i.pumps")/* +
            "WHERE i.typeInstallations = ?1 " +
            "AND i.subtype = ?2 " +
            "AND i.coolantType = ?3 " +
            "AND i.temperature = ?4 " +
            "AND i.concentration = ?5 " +
            "AND i.countMainPumps = ?6 " +
            "AND i.countSparePumps = ?7 " +
            "AND i.flowRate BETWEEN ?8 AND ?9 "
    )*/
    public List<GMInstallation> findInstallations(TypeInstallations typeInstallations,
                                                   SubtypeForGm subtype,
                                                   CoolantType coolantType,
                                                   Integer temperature,
                                                   Integer concentration,
                                                   Integer countMainPumps,
                                                   Integer countSparePumps,
                                                   Integer minFlowRate,
                                                   Integer maxFlowRate);

}
