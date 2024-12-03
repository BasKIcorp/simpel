package org.simpel.pumps.pumpssimple.repository;

import org.simpel.pumps.pumpssimple.model.Photo;
import org.simpel.pumps.pumpssimple.model.Pump;
import org.simpel.pumps.pumpssimple.model.Series;
import org.simpel.pumps.pumpssimple.model.enums.PumpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PumpRepository extends JpaRepository<Pump, Long> {
    List<Pump> findByType(PumpType pumpType);
    List<Pump> findBySeries(Series series );
    List<Pump> findBySeriesIn(List<Series> series);

}
