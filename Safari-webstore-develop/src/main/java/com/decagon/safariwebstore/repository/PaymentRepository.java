package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.Category;
import com.decagon.safariwebstore.model.Payment;
import com.decagon.safariwebstore.model.Product;
import com.decagon.safariwebstore.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findPaymentByOrderId(Long orderId);
    List<Payment> findAll();
}
