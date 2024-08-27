package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.HozPitInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HozPitRepository extends JpaRepository<HozPitInstallation,Long> {
}
