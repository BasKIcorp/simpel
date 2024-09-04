package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallationPointRepository extends JpaRepository<InstallationPoint, Long> {
    List<InstallationPoint> findByParentInstallations(ParentInstallations installations);
}
