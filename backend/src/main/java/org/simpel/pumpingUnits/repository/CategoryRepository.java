package org.simpel.pumpingUnits.repository;

import org.simpel.pumpingUnits.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
