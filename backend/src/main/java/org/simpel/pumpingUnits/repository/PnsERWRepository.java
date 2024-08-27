package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PnsERWRepository extends JpaRepository<PNSInstallationERW, Long> {

}
