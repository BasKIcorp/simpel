package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GMRepository extends JpaRepository<GMInstallation, Long> {

}
