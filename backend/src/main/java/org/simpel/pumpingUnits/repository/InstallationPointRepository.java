package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.installation.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallationPointRepository extends JpaRepository<Point, Long> {

}
