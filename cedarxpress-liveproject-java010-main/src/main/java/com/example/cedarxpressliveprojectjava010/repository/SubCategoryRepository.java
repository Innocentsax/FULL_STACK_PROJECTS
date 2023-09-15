package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    Optional<SubCategory> findSubCategoryBySubCategoryName(String subCategoryName);
}