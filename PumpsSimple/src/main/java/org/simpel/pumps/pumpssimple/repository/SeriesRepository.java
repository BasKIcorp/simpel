package org.simpel.pumps.pumpssimple.repository;

import org.simpel.pumps.pumpssimple.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, String> {

}
