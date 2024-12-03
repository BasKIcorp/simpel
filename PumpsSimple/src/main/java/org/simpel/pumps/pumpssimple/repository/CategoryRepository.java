package org.simpel.pumps.pumpssimple.repository;

import org.simpel.pumps.pumpssimple.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
