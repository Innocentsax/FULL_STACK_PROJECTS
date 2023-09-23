package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.product.IntangibleProduct;
import com.decagon.fitnessoapp.model.product.TangibleProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IntangibleProductRepository extends JpaRepository<IntangibleProduct, Long> {

    @Query("SELECT i FROM IntangibleProduct i WHERE CONCAT(i.category, i.productName, i.description) LIKE %?1%")
    List<IntangibleProduct> findIntangibleProductByCategoryOrProductNameOrByDescription(String freeText);
}
