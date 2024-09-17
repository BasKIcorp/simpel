package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepo  extends JpaRepository<Material, String> {

}
