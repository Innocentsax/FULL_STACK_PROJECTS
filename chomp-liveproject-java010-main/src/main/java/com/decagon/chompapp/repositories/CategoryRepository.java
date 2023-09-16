package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional< Category> findCategoryByCategoryName(String categoryName);
    Optional<Category> findByCategoryName(String categoryName);

}
