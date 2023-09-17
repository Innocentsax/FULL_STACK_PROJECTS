package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.Category;
import com.decagon.safariwebstore.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findByName(String name);
}
