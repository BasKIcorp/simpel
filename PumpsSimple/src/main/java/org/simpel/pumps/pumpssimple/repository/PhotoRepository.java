package org.simpel.pumps.pumpssimple.repository;

import org.simpel.pumps.pumpssimple.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByPumpId(long id);
}
