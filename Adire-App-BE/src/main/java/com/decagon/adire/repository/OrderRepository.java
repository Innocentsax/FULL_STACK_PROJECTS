package com.decagon.adire.repository;

import com.decagon.adire.entity.Designer;
import com.decagon.adire.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.util.List;
@Repository

public interface OrderRepository extends JpaRepository<Order, String> {


 List<Order> findByCreatedDateBetween(Timestamp start, Timestamp end);

//    @Query("SELECT o FROM Order o WHERE o.designer = :designer ORDER BY o.createdDate DESC")
//    Page<Order> findOrdersByDesigner(Designer designer, Pageable pageable);
    List<Order> findAllByDesigner(Designer designer);

}
