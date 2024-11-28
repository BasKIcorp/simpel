package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SeriesRepository extends JpaRepository<Series, String> {}

