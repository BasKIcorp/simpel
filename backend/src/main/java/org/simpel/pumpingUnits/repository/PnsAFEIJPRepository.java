package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.PNSInstallationAFEIJP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PnsAFEIJPRepository extends JpaRepository<PNSInstallationAFEIJP, Long> {
}
